package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        var query = "title:38% discount;";
        var pageable = createPageable(0, 1, null);

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter).stream().filter(e -> e.getTitle().equals("38% discount")).collect(Collectors.toList());
        var actual = discountService.findAll(query, pageable).getContent();

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

        assertNotNull(actual.getCategory());
        assertNotNull(actual.getVendor());
        assertEquals(expected.getCategory().getId(), actual.getCategory().getId());
        assertEquals(expected.getVendor().getId(), actual.getVendor().getId());
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

        assertNotNull(actual.getCategory());
        assertNotNull(actual.getVendor());
        assertEquals(expected.getCategoryId(), actual.getCategory().getId());
        assertEquals(expected.getVendorId(), actual.getVendor().getId());
    }

    private void matchAll(List<Discount> expected, List<DiscountResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }

    private Pageable createPageable(int page, int size, String sortRule) {
        Sort sort = null;
        if (sortRule != null && !sortRule.isEmpty()) {
            Direction sortDirection = sortRule.toLowerCase().contains("desc")
                    ? Direction.DESC
                    : Direction.ASC;
            sort = Sort.by(sortDirection, sortRule);
        }
        return sort != null
                ? PageRequest.of(page, size, sort)
                : PageRequest.of(page, size);
    }
}
