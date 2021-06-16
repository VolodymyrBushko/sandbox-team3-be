package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.EntityNotFoundException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/discount-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class DiscountServiceIntegrationTest {

    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountRepository discountRepository;

    @Test
    void shouldFindDiscountById() {
        var id = 1L;
        var expected = discountRepository.findById(id).get();
        var actual = discountService.findById(id);

        matchOne(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfNoDiscountFoundById() {
        var id = 3L;
        assertThrows(EntityNotFoundException.class, () -> {
            discountService.findById(id);
        });
    }

    @Test
    void shouldFindAllDiscounts() {
        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);
        var actual = discountService.findAll();

        matchAll(expected, actual);
    }

    @Test
    void shouldCreateDiscount() {
        var expected = createDiscountRequest();
        var actual = discountService.create(expected);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        matchOne(expected, actual);
    }

    @Test
    void shouldDiscountUpdateById() {
        var id = 1L;
        var expected = createDiscountRequest();
        var actual = discountService.update(id, expected);

        assertNotNull(actual);
        assertEquals(actual.getId(), id);

        matchOne(expected, actual);
    }

    private DiscountRequestVO createDiscountRequest() {
        var title = "title";
        var shortDescription = "shortDescription";
        var description = "description";
        var imageUrl = "http://localhost/images/img.png";
        var flatAmount = BigDecimal.valueOf(100.15);
        var percentage = BigDecimal.valueOf(10.15);
        var price = BigDecimal.valueOf(50.15);
        var startDate = LocalDateTime.now();
        var expirationDate = startDate.plusDays(1);
        var quantity = 10;
        var perUser = 1;
        var categoryId = 10L;
        var vendorId = 10L;

        return DiscountRequestVO.builder()
                .title(title)
                .shortDescription(shortDescription)
                .description(description)
                .imageUrl(imageUrl)
                .flatAmount(flatAmount)
                .percentage(percentage)
                .price(price)
                .startDate(startDate)
                .expirationDate(expirationDate)
                .quantity(quantity)
                .perUser(perUser)
                .categoryId(categoryId)
                .vendorId(vendorId)
                .build();
    }

    private void matchOne(Discount expected, DiscountResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getShortDescription(), actual.getShortDescription());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getFlatAmount(), actual.getFlatAmount());
        assertEquals(expected.getPercentage(), actual.getPercentage());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getQuantity(), actual.getQuantity());
        assertEquals(expected.getPerUser(), actual.getPerUser());
        assertEquals(expected.getCategory().getId(), actual.getCategoryId());
        assertEquals(expected.getVendor().getId(), actual.getVendorId());
    }

    private void matchOne(DiscountRequestVO expected, DiscountResponseVO actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getShortDescription(), actual.getShortDescription());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getFlatAmount(), actual.getFlatAmount());
        assertEquals(expected.getPercentage(), actual.getPercentage());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getQuantity(), actual.getQuantity());
        assertEquals(expected.getPerUser(), actual.getPerUser());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getVendorId(), actual.getVendorId());
    }

    private void matchAll(List<Discount> expected, List<DiscountResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }
}
