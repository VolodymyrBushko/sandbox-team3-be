package com.exadel.discountwebapp.location.controller;

import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/location2-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
public class LocationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private LocationRepository repository;
    @Autowired
    private LocationService service;

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetAllLocationsWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllLocationResponseVO()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetAllLocationWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getAllLocationResponseVO()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetLocationByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.country").value("Ukraine"))
                .andExpect(jsonPath("$.city").value("Kyiv"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldGetLocationByIdWithRoleADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/2"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.country").value("Ukraine"))
                .andExpect(jsonPath("$.city").value("Lviv"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldCreateLocationByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.country").value("Belarus"))
                .andExpect(jsonPath("$.city").value("Minsk"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldUpdateLocationByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.country").value("Belarus"))
                .andExpect(jsonPath("$.city").value("Minsk"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void shouldDeleteLocationByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(repository.existsById(1L)).isFalse();
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    private String getAllLocationResponseVO() throws JsonProcessingException {
        List<LocationResponseVO> responseVO = service.findAll();
        return mapper.writeValueAsString(responseVO);
    }

    private String getLocationRequestVO() throws JsonProcessingException {
        var requestVO = new LocationRequestVO().builder()
                .country("Belarus")
                .city("Minsk")
                .build();

        return mapper.writeValueAsString(requestVO);
    }
}
