package com.exadel.discountwebapp.tag.controller;

import com.exadel.discountwebapp.tag.mapper.TagMapper;
import com.exadel.discountwebapp.tag.repository.TagRepository;
import com.exadel.discountwebapp.tag.service.TagService;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
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

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tag-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class TagControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TagRepository repository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagService service;

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllTagsWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllTagsAsJsonData()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllTagsWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllTagsAsJsonData()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetTagByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags/{id}", 2))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("wear"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetTagByIdWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags/{id}", 4))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.name").value("food"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldFindAllTagsByCategoryIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags/category/{categoryId}", 10))
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(getTagsByCategoryId(10L)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldFindAllTagsByCategoryIdWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags/category/{categoryId}", 20))
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(getTagsByCategoryId(20L)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldCreateTagByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getTagRequestVO()))
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.name").value("pizza"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldUpdateTagByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tags/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getTagRequestVO()))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("pizza"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteTagByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tags/{id}", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(repository.existsById(3L)).isFalse();
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getTagRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tags/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getTagRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tags/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tags"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tags/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tags/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private String getAllTagsAsJsonData() throws JsonProcessingException {
        var responseVO = service.findAll();
        return mapper.writeValueAsString(responseVO);
    }

    private String getTagsByCategoryId(Long id) throws JsonProcessingException {
        var tags = Lists.newArrayList(repository.findAll());
        var result = tags.stream()
                .filter(e -> e.getCategory().getId() == id)
                .map(e -> tagMapper.toVO(e))
                .collect(Collectors.toList());
        return mapper.writeValueAsString(result);
    }

    private String getTagRequestVO() throws JsonProcessingException {
        var requestVO = TagRequestVO.builder()
                .name("pizza")
                .categoryId(10L)
                .build();

        return mapper.writeValueAsString(requestVO);
    }
}
