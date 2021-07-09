package com.exadel.discountwebapp.vendor.controller;

import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/vendor-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class VendorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private VendorRepository repository;

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllVendorWithRoleUser() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());

        Assertions.assertNotNull(actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllVendorsWithRoleAdmin() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertNotNull(actual);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetVendorByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors/{id}", "1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Sport Life"))
                .andExpect(jsonPath("$.description").value("Sport Life - a chain of casual fitness centers"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetVendorByIdWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors/{id}", "2"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("Domino`s Pizza"))
                .andExpect(jsonPath("$.description").value("Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetVendorByTitleWithRoleUser() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors?title=title*.*Sport"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());

        Assertions.assertNotNull(actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetVendorByTitleWithRoleAdmin() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors?title=title*.*Sport"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content[0].title").value("Domino`s Pizza"))
                .andExpect(jsonPath("$.content[1].title").value("Sport Life"))
                .andExpect(jsonPath("$.content[2].title").value("TUI"))
                .andExpect(jsonPath("$.content[0].locations[0].city").value("Lviv"))
                .andExpect(jsonPath("$.content[0].locations[0].city").value("Lviv"))
                .andExpect(jsonPath("$.content[0].locations[0].city").value("Lviv"))
                .andExpect(status().isOk());
        Assertions.assertNotNull(actual);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldCreateVendorByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getVendorRequestVOAsJson()))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.title").value("title3"))
                .andExpect(jsonPath("$.imageUrl").value("http://localhost/images/img3.png"))
                .andExpect(jsonPath("$.locations[0].id").value("1"))
                .andExpect(jsonPath("$.locations[0].city").value("Kyiv"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldUpdateVendorByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/vendors/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getVendorRequestVOAsJson()))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("title3"))
                .andExpect(jsonPath("$.imageUrl").value("http://localhost/images/img3.png"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteVendorByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vendors/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(repository.existsById(1L)).isFalse();
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getVendorRequestVOAsJson()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/vendors/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getVendorRequestVOAsJson()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vendors/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendors"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vendors"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/vendors/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vendors/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private String getVendorRequestVOAsJson() throws JsonProcessingException {
        var requestVO = VendorRequestVO.builder()
                .title("title3")
                .description("description3")
                .imageUrl("http://localhost/images/img3.png")
                .email("testemail3@gmail.com")
                .locationIds(List.of(1L))
                .build();

        return mapper.writeValueAsString(requestVO);
    }

}
