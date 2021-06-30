package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/database-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
public class DiscountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DiscountRepository repository;
    @Autowired
    DiscountMapper discountMapper;
    @Autowired
    DiscountService service;

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetAllDiscountsWithRoleUser() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/discounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
        Assertions.assertNotNull(actual);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetAllDiscountsWithRoleAdmin() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/discounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertNotNull(actual);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetDiscountByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/discounts/{id}", "1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("38% discount"))
                .andExpect(jsonPath("$.imageUrl").value("sport_life_discount_image_1.jsp"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetDiscountByIdWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/discounts/{id}", "2"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("50% discount"))
                .andExpect(jsonPath("$.imageUrl").value("domino`s_pizza_discount_image_1.jsp"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldCreateDiscountByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDiscountRequestVOAsJson()))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.title").value("title333"))
                .andExpect(jsonPath("$.imageUrl").value("http://localhost/images/img3.png"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldUpdateDiscountByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/discounts/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDiscountRequestVOAsJson()))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("title333"))
                .andExpect(jsonPath("$.imageUrl").value("http://localhost/images/img3.png"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldDeleteDiscountByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/discounts/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(repository.existsById(1L)).isFalse();
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDiscountRequestVOAsJson()))
                .andExpect(MockMvcResultMatchers.status().reason("Forbidden"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/discounts/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDiscountRequestVOAsJson()))
                .andExpect(MockMvcResultMatchers.status().reason("Forbidden"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/discounts/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Forbidden"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/discounts"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/discounts"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/discounts/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/discounts/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    private String getDiscountRequestVOAsJson() throws JsonProcessingException {
        var data = LocalDateTime.now();

        var requestVO = DiscountRequestVO.builder()
                .title("title333")
                .shortDescription("shortDescription3")
                .description("description3")
                .imageUrl("http://localhost/images/img3.png")
                .flatAmount(BigDecimal.valueOf(150.15))
                .percentage(BigDecimal.valueOf(17.1))
                .price(BigDecimal.valueOf(55.15))
                .startDate(LocalDateTime.now())
                .expirationDate(data.plusDays(1))
                .quantity(10)
                .perUser(1)
                .categoryId(1L)
                .vendorId(1L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper.writeValueAsString(requestVO);
    }
}
