package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/vendor-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class VendorServiceIntegrationTest {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorRepository vendorRepository;

    @Test
    void shouldFindVendorById() {
        var id = 1L;
        var expected = vendorRepository.findById(id).get();
        var actual = vendorService.findById(id);

        matchOne(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfNoVendorFoundById() {
        var id = 345345L;
        assertThrows(EntityNotFoundException.class, () -> {
            vendorService.findById(id);
        });
    }

    @Test
    void shouldFindVendorByTitle() {
        var title = "Sport Life";
        var expected = vendorRepository.findByTitle(title).get();
        var actual = vendorService.findByTitle(title);

        matchOne(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfNoVendorFoundByTitle() {
        var title = "Test wrong title";
        assertThrows(EntityNotFoundException.class, () -> {
            vendorService.findByTitle(title);
        });
    }

    @Test
    void shouldFindAllVendors() {
        var query = "title:Sport Life;";
        var pageable = createPageable(0, 1, null);

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter).stream().filter(e -> e.getTitle().equals("Sport Life")).collect(Collectors.toList());
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldCreateVendor() {
        var expected = createVendorRequest();
        var actual = vendorService.create(expected);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        matchOne(expected, actual);
    }

    @Test
    void shouldUpdateVendorById() {
        var id = 1L;
        var expected = createVendorRequest();
        var actual = vendorService.update(id, expected);

        assertNotNull(actual);
        assertEquals(actual.getId(), id);

        matchOne(expected, actual);
    }

    private VendorRequestVO createVendorRequest() {
        var title = "title";
        var description = "description";
        var imageUrl = "http://localhost/images/img.png";
        var email = "testemail@gmail.com";
        var locationId = 1L;

        return VendorRequestVO.builder()
                .title(title)
                .description(description)
                .imageUrl(imageUrl)
                .email(email)
                .locationId(locationId)
                .build();
    }

    private void matchAll(List<Vendor> expected, List<VendorResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }

    private void matchOne(Vendor expected, VendorResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getLocation().getId(), actual.getLocation().getId());
    }

    private void matchOne(VendorRequestVO expected, VendorResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getLocationId(), actual.getLocation().getId());
    }

    private Pageable createPageable(int page, int size, String sortRule) {
        Sort sort = null;
        if (sortRule != null && !sortRule.isEmpty()) {
            Sort.Direction sortDirection = sortRule.toLowerCase().contains("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sort = Sort.by(sortDirection, sortRule);
        }
        return sort != null
                ? PageRequest.of(page, size, sort)
                : PageRequest.of(page, size);
    }
}
