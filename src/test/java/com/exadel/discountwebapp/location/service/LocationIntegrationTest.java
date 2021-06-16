package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/location-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class LocationIntegrationTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldFindLocationById() {
        var id = 1L;
        var expected = locationRepository.findById(id).get();
        var actual = locationService.findById(id);

        matchOne(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfNoLocationFoundById() {
        var id = 345345L;
        assertThrows(EntityNotFoundException.class, () -> {
            locationService.findById(id);
        });
    }

    @Test
    void shouldFindAllLocations() {
        var expectedIter = locationRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);
        var actual = locationService.findAll();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllLocationsByCountry() {
        var country = "Ukraine";
        var expectedIter = locationRepository.findAllByCountry(country);
        var expected = Lists.newArrayList(expectedIter);
        var actual = locationService.findAllByCountry(country);

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllLocationsByCity() {
        var city = "Kyiv";
        var expectedIter = locationRepository.findAllByCity(city);
        var expected = Lists.newArrayList(expectedIter);
        var actual = locationService.findAllByCity(city);

        matchAll(expected, actual);
    }

    @Test
    void shouldCreateLocation() {
        var expected = createLocationRequest();
        var actual = locationService.create(expected);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        matchOne(expected, actual);
    }

    private LocationRequestVO createLocationRequest() {
        var country = "Belarus";
        var city = "Minsk";

        return LocationRequestVO.builder()
                .country(country)
                .city(city)
                .build();
    }

    @Test
    void shouldUpdateLocationById() {
        var id = 1L;
        var expected = createLocationRequest();
        var actual = locationService.update(id, expected);

        assertNotNull(actual);
        assertEquals(actual.getId(), id);

        matchOne(expected, actual);
    }

    private void matchAll(List<Location> expected, List<LocationResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }

    private void matchOne(Location expected, LocationResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getCity(), actual.getCity());
    }

    private void matchOne(LocationRequestVO expected, LocationResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getCity(), actual.getCity());
    }
}
