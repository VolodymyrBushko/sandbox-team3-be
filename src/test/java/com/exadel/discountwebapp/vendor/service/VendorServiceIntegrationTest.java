package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
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
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/vendor-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
public class VendorServiceIntegrationTest {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorRepository vendorRepository;

    @Test
    public void shouldFindVendorById() {
        var id = 1L;
        var expected = vendorRepository.findById(id).get();
        var actual = vendorService.findById(id);

        matchOne(expected, actual);
    }

    @Test
    public void shouldThrowExceptionIfNoVendorFoundById() {
        var id = 345345L;
        assertThrows(EntityNotFoundException.class, () -> {
            vendorService.findById(id);
        });
    }

    @Test
    public void shouldFindVendorByTitle() {
        var title = "Sport Life";
        var expected = vendorRepository.findByTitle(title).get();
        var actual = vendorService.findByTitle(title);

        matchOne(expected, actual);
    }

    @Test
    public void shouldThrowExceptionIfNoVendorFoundByTitle() {
        var title = "Test wrong title";
        assertThrows(EntityNotFoundException.class, () -> {
            vendorService.findByTitle(title);
        });
    }

    @Test
    public void shouldFindAllVendors() {
        var expectedIter = vendorRepository.findAll();
        var expected = iterableToList(expectedIter);
        var actual = vendorService.findAll();

        matchAll(expected, actual);
    }

    @Test
    public void shouldCreateVendor() {
        var expected = createVendorRequest();
        var actual = vendorService.create(expected);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        matchOne(expected, actual);
    }

    @Test
    public void shouldUpdateVendorById() {
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

    private <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
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
        assertEquals(expected.getLocation().getId(), actual.getLocationId());
    }

    private void matchOne(VendorRequestVO expected, VendorResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getLocationId(), actual.getLocationId());
    }
}
