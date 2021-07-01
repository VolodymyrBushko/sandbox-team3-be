package com.exadel.discountwebapp.role.controller;

import com.exadel.discountwebapp.role.service.RoleService;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/role-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
public class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RoleService service;

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetAllRolesWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllRolesResponseVO()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetAllRolesWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllRolesResponseVO()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetRoleByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("USER"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetRoleByIdWithRoleADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles/2"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("ADMIN"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetByIdRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private String getAllRolesResponseVO() throws JsonProcessingException {
        List<RoleResponseVO> responseVO = service.getAllRoles();
        return mapper.writeValueAsString(responseVO);
    }
}
