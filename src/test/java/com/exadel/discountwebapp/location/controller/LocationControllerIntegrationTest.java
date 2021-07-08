//package com.exadel.discountwebapp.location.controller;
//
//import com.exadel.discountwebapp.location.entity.Location;
//import com.exadel.discountwebapp.location.repository.LocationRepository;
//import com.exadel.discountwebapp.location.service.LocationService;
//import com.exadel.discountwebapp.location.vo.location.LocationRequestVO;
//import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.collect.Lists;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/location2-init.sql")
//@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
//class LocationControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper mapper;
//    @Autowired
//    private LocationRepository locationRepository;
//    @Autowired
//    private LocationService locationService;
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void shouldGetAllLocationsWithRoleUser() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(getAllLocationResponseVO()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldGetAllLocationWithRoleAdmin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json(getAllLocationResponseVO()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void shouldGetLocationByIdWithRoleUser() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/4"))
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id").value("4"))
//                .andExpect(jsonPath("$.countryCode").value("UA"))
//                .andExpect(jsonPath("$.city").value("Lviv"))
//                .andExpect(jsonPath("$.addressLine").value("Gorodockogo, 9"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldGetLocationByIdWithRoleADMIN() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/2"))
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id").value("2"))
//                .andExpect(jsonPath("$.countryCode").value("UA"))
//                .andExpect(jsonPath("$.city").value("Lviv"))
//                .andExpect(jsonPath("$.addressLine").value("Sichovyh Strilciv, 52"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    void shouldCreateLocationByAdmin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(getLocationRequestVO()))
//                .andExpect(jsonPath("$.id").value("6"))
//                .andExpect(jsonPath("$.countryCode").value("BY"))
//                .andExpect(jsonPath("$.city").value("Minsk"))
//                .andExpect(jsonPath("$.addressLine").value("Belinsky, 22"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    void shouldUpdateLocationByAdmin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(getLocationRequestVO()))
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.countryCode").value("BY"))
//                .andExpect(jsonPath("$.city").value("Minsk"))
//                .andExpect(jsonPath("$.addressLine").value("Belinsky, 22"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    void shouldDeleteLocationByAdmin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "1"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//        assertThat(locationRepository.existsById(1L)).isFalse();
//    }
//
//    @Test
//    @WithMockUser(authorities = "USER")
//    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToCreateResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(getLocationRequestVO()))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(authorities = "USER")
//    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToUpdateResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(getLocationRequestVO()))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(authorities = "USER")
//    void shouldGet403ErrorIfUserWithoutAdminRightsTryToGetAccessToDeleteResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "2"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    void shouldGet403ErrorWhenNotAuthorizedUserTryToUseGetResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/locations"))
//                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseCreateResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations"))
//                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseUpdateResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/locations/{id}", "2"))
//                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    void shouldGet403ErrorWhenNotAuthenticatedUserTryToUseDeleteResource() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/locations/{id}", "2"))
//                .andExpect(MockMvcResultMatchers.status().reason("Access Denied"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    private String getAllLocationResponseVO() throws JsonProcessingException {
//        var locationCount = (int) locationRepository.count();
//        var pageable = PageRequest.of(0, locationCount);
//        List<LocationResponseVO> responseVO = locationService.findAll(null, pageable).getContent();
//        Iterable<Location> locations = locationRepository.findAll();
//
//        ArrayList<Location> locations1 = Lists.newArrayList(locations);
//
//
//        String result = mapper.writeValueAsString(locations1);
//
//
//
//        String s = mapper.writeValueAsString(responseVO);
//        List<String> rez = new ArrayList<>();
//        for (LocationResponseVO el:responseVO) {
//            rez.add(mapper.writeValueAsString(el));
//        }
//
////        LocationResponseVO[] asArray = mapper.readValue(s, LocationResponseVO[].class);
////        List<LocationResponseVO> myObjects = Arrays.asList(mapper.readValue(s, LocationResponseVO[].class));
//        System.out.println(rez);
//        System.out.println(rez.get(0));
//        System.out.println(rez.toString());
//        return result;
//    }
//
//    private String getLocationRequestVO() throws JsonProcessingException {
//        var requestVO = new LocationRequestVO().builder()
//                .countryCode("BY")
//                .city("Minsk")
//                .addressLine("Belinsky, 22")
//                .build();
//
//        return mapper.writeValueAsString(requestVO);
//    }
//}
