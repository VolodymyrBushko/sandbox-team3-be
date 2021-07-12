package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.filter.SpecificationBuilder;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.location.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
        var locationCount = (int) locationRepository.count();
        var pageable = PageRequest.of(0, locationCount);
        var actual = locationService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllLocationsByCountry() {
        var query = "country.countryCode:UA";

        var pageable = PageRequest.of(0, 1);
        SpecificationBuilder<Location> specificationBuilder = new SpecificationBuilder<>();
        Specification<Location> specification = specificationBuilder.fromQuery(query);
        var expected = locationRepository.findAll(specification, pageable);

        var actual = locationService.findAll(query, pageable);

        matchAll(expected.getContent(), actual.getContent());
    }

    @Test
    void shouldFindAllLocationsByCity() {
        var city = "Kyiv";
        var query = "city:Kyiv";

        var pageable = PageRequest.of(0, 1);

        SpecificationBuilder<Location> specificationBuilder = new SpecificationBuilder<>();
        Specification<Location> specification = specificationBuilder.fromQuery(query);

        Page<Location> expected = locationRepository.findAll(specification, pageable);

        var actual = locationService.findAll(query, pageable);

        matchAll( expected.getContent(), actual.getContent());
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
        var countryCode = "UA";
        var city = "Kyiv";
        var addressLine = "Khreshchatyk, 22";
        return LocationRequestVO.builder()
                .countryCode(countryCode)
                .city(city)
                .addressLine(addressLine)
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
        assertEquals(expected.getCountry().getCountryCode(), actual.getCountryCode());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getAddressLine(), actual.getAddressLine());
    }

    private void matchOne(LocationRequestVO expected, LocationResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getCountryCode(), actual.getCountryCode());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getAddressLine(), actual.getAddressLine());
    }
}
