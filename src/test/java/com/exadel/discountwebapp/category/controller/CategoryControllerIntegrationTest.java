package com.exadel.discountwebapp.category.controller;

import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/category-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CategoryService service;

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllCategoriesWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllCategoriesAsJsonData()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetCategoryByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("category-1"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllCategoriesWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllCategoriesAsJsonData()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetCategoryByIdWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/2"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("category-2"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldCreateCategoryByAdmin() throws Exception {
        CategoryRequestVO requestVO = new CategoryRequestVO().builder()
                .title("category-3")
                .build();

        String jsonRequest = mapper.writeValueAsString(requestVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(jsonPath("$.title").value("category-3"))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldUpdateCategoryByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestVO()))
                .andExpect(jsonPath("$.title").value("categoryUpdate-2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteCategoryByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(repository.existsById(2L)).isFalse();
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private String getAllCategoriesAsJsonData() throws JsonProcessingException {
        var responseVO = service.findAll();
        return mapper.writeValueAsString(responseVO);
    }

    private String getRequestVO() throws JsonProcessingException {
        var requestVO = new CategoryRequestVO().builder()
                .title("categoryUpdate-2")
                .build();

        return mapper.writeValueAsString(requestVO);
    }
}