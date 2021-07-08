package com.exadel.discountwebapp.location.controller;

import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.location.LocationRequestVO;
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
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/location2-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class LocationControllerIntegrationTestModif {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetAllLocationsWithRoleUser() throws Exception {
        var actual = mockMvc.perform(MockMvcRequestBuilders.get("/api/locations?search=offset=0&pageSize=10"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllLocationWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content[*].id").isNotEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldGetLocationByIdWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/4"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.countryCode").value("UA"))
                .andExpect(jsonPath("$.city").value("Lviv"))
                .andExpect(jsonPath("$.addressLine").value("Gorodockogo, 9"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetLocationByIdWithRoleADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/2"))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.countryCode").value("UA"))
                .andExpect(jsonPath("$.city").value("Lviv"))
                .andExpect(jsonPath("$.addressLine").value("Sichovyh Strilciv, 52"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldCreateLocationByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.countryCode").value("BY"))
                .andExpect(jsonPath("$.city").value("Minsk"))
                .andExpect(jsonPath("$.addressLine").value("Belinsky, 22"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldUpdateLocationByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.countryCode").value("BY"))
                .andExpect(jsonPath("$.city").value("Minsk"))
                .andExpect(jsonPath("$.addressLine").value("Belinsky, 22"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldDeleteLocationByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(locationRepository.existsById(1L)).isFalse();
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getLocationRequestVO()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER")
    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "2"))
                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    private String getLocationRequestVO() throws JsonProcessingException {
        var requestVO = new LocationRequestVO().builder()
                .countryCode("BY")
                .city("Minsk")
                .addressLine("Belinsky, 22")
                .build();

        return mapper.writeValueAsString(requestVO);
    }
}
