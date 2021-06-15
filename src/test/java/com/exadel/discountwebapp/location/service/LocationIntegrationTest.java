package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/location-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
public class LocationIntegrationTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void shouldFindLocationById() {
        var id = 1L;
        var expected = locationRepository.findById(id).get();
        var actual = locationService.findById(id);

        matchOne(expected, actual);
    }

    @Test
    public void shouldThrowExceptionIfNoLocationFoundById() {
        var id = 345345L;
        assertThrows(EntityNotFoundException.class, () -> {
            locationService.findById(id);
        });
    }

    @Test
    public void shouldFindAllLocations() {
        var expectedIter = locationRepository.findAll();
        var expected = iterableToList(expectedIter);
        var actual = locationService.findAll();

        matchAll(expected, actual);
    }

    @Test
    public void shouldFindAllLocationsByCountry() {
        var country = "Ukraine";
        var expectedIter = locationRepository.findAllByCountry(country);
        var expected = iterableToList(expectedIter);
        var actual = locationService.findAllByCountry(country);

        matchAll(expected, actual);
    }

    @Test
    public void shouldFindAllLocationsByCity() {
        var city = "Kyiv";
        var expectedIter = locationRepository.findAllByCity(city);
        var expected = iterableToList(expectedIter);
        var actual = locationService.findAllByCity(city);

        matchAll(expected, actual);
    }

    @Test
    public void shouldCreateLocation() {
        var expected = createLocationRequest();
        var actual = locationService.create(expected);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        matchOne(expected, actual);
    }

    private LocationRequestVO createLocationRequest() {
        var country = "Belarus";
        var city = "Minsk";
        var vendorId = 1L;

        return LocationRequestVO.builder()
                .country(country)
                .city(city)
                .build();
    }

    @Test
    public void shouldUpdateLocationById() {
        var id = 1L;
        var expected = createLocationRequest();
        var actual = locationService.update(id, expected);

        assertNotNull(actual);
        assertEquals(actual.getId(), id);

        matchOne(expected, actual);
    }


    private <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
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
