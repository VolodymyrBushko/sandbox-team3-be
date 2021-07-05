package com.exadel.discountwebapp.user.controller;

import com.exadel.discountwebapp.security.SigninVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/user-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldReturn200WithCorrectCredentials() throws Exception {
        SigninVO signinVO = new SigninVO("ivan_ivanov@gmail.com", "1111");
        String json = mapper.writeValueAsString(signinVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WithWrongPassword() throws Exception {
        SigninVO signinVO = new SigninVO("ivan_ivanov@gmail.com", "111111");
        String json = mapper.writeValueAsString(signinVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WithWrongEmail() throws Exception {
        SigninVO signinVO = new SigninVO("ivan@gmail.com", "1111");
        String json = mapper.writeValueAsString(signinVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException))
                .andExpect(result -> assertEquals("Login or Password is wrong! Try again!", result.getResolvedException().getMessage()));
    }
}

