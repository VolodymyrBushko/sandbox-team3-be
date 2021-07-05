package com.exadel.discountwebapp.vendor.mapper;

import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/vendor-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class VendorMapperTest {

    @Autowired
    private VendorMapper vendorMapper;

    @Autowired
    private VendorRepository vendorRepository;

    @Test
    void shouldMapEntityToVO(){
        var id = 1L;
        var actual = vendorRepository.findById(id).get();

        var expected = vendorMapper.toVO(actual);

        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getImageUrl(), expected.getImageUrl());
        assertEquals(actual.getEmail(), expected.getEmail());

        assertEquals(actual.getLocation().getId(), expected.getLocation().getId());
        assertEquals(actual.getLocation().getCity(), expected.getLocation().getCity());
        assertEquals(actual.getLocation().getCountryCode().getCountryCode(), expected.getLocation().getCountryCode());
    }

    @Test
    void shouldMapVOToEntity(){
        var actual = new  VendorRequestVO();
        actual.setTitle("title");
        actual.setDescription("description");
        actual.setEmail("testemail@gmail.com");
        actual.setImageUrl("http://localhost/images/img.png");
        actual.setLocationId(1L);

        var expected = vendorMapper.toEntity(actual);

        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getImageUrl(), expected.getImageUrl());
        assertEquals(actual.getEmail(), expected.getEmail());
        assertEquals(actual.getLocationId(), expected.getLocation().getId());
    }
}
