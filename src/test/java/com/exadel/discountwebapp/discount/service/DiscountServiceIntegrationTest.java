package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.exception.exception.client.IncorrectFilterInputException;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        var id = 12345L;
        assertThrows(EntityNotFoundException.class, () -> {
            discountService.findById(id);
        });
    }

    @Test
    void shouldFindAllDiscounts() {
        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsAndSortByTitleWithDirAsc() {
        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);
        expected.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));

        var sortField = "title";
        var sortDir = Sort.Direction.ASC;
        var sort = Sort.by(sortDir, sortField);

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount, sort);
        var actual = discountService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsAndSortByTitleWithDirDesc() {
        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);
        expected.sort((a, b) -> b.getTitle().compareTo(a.getTitle()));

        var sortField = "title";
        var sortDir = Sort.Direction.DESC;
        var sort = Sort.by(sortDir, sortField);

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount, sort);
        var actual = discountService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereIdLessThanTwo() {
        var id = 2L;
        var query = "id<" + id;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getId() < id).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereIdGreaterThanTwo() {
        var id = 2L;
        var query = "id>" + id;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getId() > id).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereCreatedDateBetweenTwoDates() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        var firstDate = LocalDateTime.parse("2021-06-06 17:22:21", formatter);
        var secondDate = LocalDateTime.parse("2023-06-06 17:22:21", formatter);
        var query = String.format("created>%s;created<%s", firstDate, secondDate);

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getCreated().isAfter(firstDate) && e.getCreated().isBefore(secondDate)).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereTitleEqualsHappyDrink() {
        var title = "happy drink";
        var query = "title:" + title;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereTitleStartsWithHappy() {
        var title = "happy";
        var query = "title:*" + title;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getTitle().toLowerCase().startsWith(title)).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereTitleEndsWithDrink() {
        var title = "drink";
        var query = "title*:" + title;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getTitle().toLowerCase().endsWith(title)).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereDescriptionContainsCaffe() {
        var description = "caffe";
        var query = "description*:*" + description;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getDescription().toLowerCase().contains(description)).collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void shouldFindAllDiscountsWhereCityEqualsLviv() {
        var city = "lviv";
        var query = "locations.city:" + city;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream()
                .filter(d -> d.getLocations().stream().anyMatch(l -> l.getCity().equalsIgnoreCase(city)))
                .collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereVendorEqualsSportLife() {
        var vendor = "sport life";
        var query = "vendor.title:" + vendor;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream()
                .filter(e -> e.getVendor().getTitle().equalsIgnoreCase(vendor))
                .collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllDiscountsWhereCategoryEqualsSportsAndFitness() {
        var category = "sports and fitness";
        var query = "category.title:" + category;

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream()
                .filter(e -> e.getCategory().getTitle().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void shouldFindAllDiscountsWhereTitleStartsWithHappyAndDescriptionContainsCaffeAndCityEqualsKyiv() {
        var title = "happy";
        var description = "caffe";
        var city = "kyiv";
        var query = String.format("title:*%s;description*:*%s;locations.city:%s", title, description, city);

        var expectedIter = discountRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream()
                .filter(e -> e.getTitle().toLowerCase().startsWith(title) &&
                        e.getDescription().toLowerCase().contains(description) &&
                        e.getLocations().stream().anyMatch(l -> l.getCity().equalsIgnoreCase(city)))
                .collect(Collectors.toList());

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void shouldFindAllDiscountsWhereTagNameInFoodAndOrSport() {
        var firstTagName = "food";
        var secondTagName = "sport";

        var query = String.format("tags.name~%s,%s", firstTagName, secondTagName);

        var firstDiscount = Discount.builder()
                .id(1L)
                .title("38% discount")
                .shortDescription("an unlimited annual subscription")
                .description("38% discount on the purchase of an unlimited annual subscription to the fitness club \"Sport Life\"")
                .imageUrl("sport_life_discount_image_1.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("100.00"))
                .startDate(LocalDateTime.parse("2021-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2021-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2021-06-06T17:22:21"))
                .build();

        var secondDiscount = Discount.builder()
                .id(2L)
                .title("50% discount")
                .shortDescription("50% discount on all pizza menus")
                .description("50% discount on all pizza menus from the pizzeria \"Domino`s Pizza\"")
                .imageUrl("domino`s_pizza_discount_image_1.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("150.00"))
                .startDate(LocalDateTime.parse("2022-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2022-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2022-06-06T17:22:21"))
                .build();

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);

        var expected = List.of(firstDiscount, secondDiscount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAllPure(expected, actual);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void shouldFindAllDiscountsWhereTagId1AndOr3() {
        var firstTagId = 1L;
        var secondTagId = 3L;

        var query = String.format("tags.id~%s,%s;", firstTagId, secondTagId);

        var firstDiscount = Discount.builder()
                .id(1L)
                .title("38% discount")
                .shortDescription("an unlimited annual subscription")
                .description("38% discount on the purchase of an unlimited annual subscription to the fitness club \"Sport Life\"")
                .imageUrl("sport_life_discount_image_1.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("100.00"))
                .startDate(LocalDateTime.parse("2021-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2021-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2021-06-06T17:22:21"))
                .build();

        var secondDiscount = Discount.builder()
                .id(3L)
                .title("HappyDrink")
                .shortDescription("70% discount on all drinks menus")
                .description("70% discount on all drinks menus from the caffe \"Drink House\"")
                .imageUrl("drinks.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("150.00"))
                .startDate(LocalDateTime.parse("2023-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2023-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2023-06-06T17:22:21"))
                .build();

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);

        var expected = List.of(firstDiscount, secondDiscount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAllPure(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfFieldDoesNotExistInVendor() {
        var field = "I don't exist";
        var query = field + ":some value";

        var pageable = PageRequest.of(0, 20);

        assertThrows(IncorrectFilterInputException.class, () -> {
            discountService.findAll(query, pageable);
        });
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

    @Test
    void shouldFindAllDiscountsWhereTitleContains38Or50Percentage() {
        var firstPercentage = "38%";
        var secondPercentage = "50%";

        var query = String.format("title?*:*%s;title?*:*%s;", firstPercentage, secondPercentage);

        var firstDiscount = Discount.builder()
                .id(1L)
                .title("38% discount")
                .shortDescription("an unlimited annual subscription")
                .description("38% discount on the purchase of an unlimited annual subscription to the fitness club \"Sport Life\"")
                .imageUrl("sport_life_discount_image_1.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("100.00"))
                .startDate(LocalDateTime.parse("2021-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2021-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2021-06-06T17:22:21"))
                .build();

        var secondDiscount = Discount.builder()
                .id(2L)
                .title("50% discount")
                .shortDescription("50% discount on all pizza menus")
                .description("50% discount on all pizza menus from the pizzeria \"Domino`s Pizza\"")
                .imageUrl("domino`s_pizza_discount_image_1.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("150.00"))
                .startDate(LocalDateTime.parse("2022-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2022-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2022-06-06T17:22:21"))
                .build();

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);

        var expected = List.of(firstDiscount, secondDiscount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAllPure(expected, actual);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void shouldFindAllDiscountsWhereDescriptionContainsDrinkOrPizzaAndCategoryContainsFood() {
        var firstDesc = "drink";
        var secondDesc = "pizza";
        var category = "food";

        var query = String.format("description?*:*%s;description?*:*%s;category.title*:*%s",
                firstDesc, secondDesc, category);

        var firstDiscount = Discount.builder()
                .id(2L)
                .title("50% discount")
                .shortDescription("50% discount on all pizza menus")
                .description("50% discount on all pizza menus from the pizzeria \"Domino`s Pizza\"")
                .imageUrl("domino`s_pizza_discount_image_1.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("150.00"))
                .startDate(LocalDateTime.parse("2022-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2022-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2022-06-06T17:22:21"))
                .build();

        var secondDiscount = Discount.builder()
                .id(3L)
                .title("HappyDrink")
                .shortDescription("70% discount on all drinks menus")
                .description("70% discount on all drinks menus from the caffe \"Drink House\"")
                .imageUrl("drinks.jsp")
                .percentage(null)
                .flatAmount(new BigDecimal("150.00"))
                .startDate(LocalDateTime.parse("2023-06-06T17:22:21"))
                .expirationDate(LocalDateTime.parse("2023-12-06T17:22:21"))
                .created(LocalDateTime.parse("2021-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2023-06-06T17:22:21"))
                .build();

        var discountCount = (int) discountRepository.count();
        var pageable = PageRequest.of(0, discountCount);

        var expected = List.of(firstDiscount, secondDiscount);
        var actual = discountService.findAll(query, pageable).getContent();

        matchAllPure(expected, actual);
    }

    private DiscountRequestVO createDiscountRequest() {
        var title = "title";
        var shortDescription = "shortDescription";
        var description = "description";
        var imageUrl = "http://localhost/images/img.png";
        var flatAmount = BigDecimal.valueOf(100.15);
        var startDate = LocalDateTime.now();
        var expirationDate = startDate.plusDays(1);
        var categoryId = 10L;
        var vendorId = 10L;
        var locationIds = List.of(10L, 20L);
        var promocode = "promocode123";
        var tagIds = List.of(1L, 2L);

        return DiscountRequestVO.builder()
                .title(title)
                .shortDescription(shortDescription)
                .description(description)
                .imageUrl(imageUrl)
                .flatAmount(flatAmount)
                .startDate(startDate)
                .expirationDate(expirationDate)
                .categoryId(categoryId)
                .vendorId(vendorId)
                .tagIds(tagIds)
                .locationIds(locationIds)
                .promocode(promocode)
                .build();
    }

    private void matchOne(Discount expected, DiscountResponseVO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getShortDescription(), actual.getShortDescription());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getFlatAmount(), actual.getFlatAmount());
        assertEquals(expected.getPercentage(), actual.getPercentage());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getPromocode(), actual.getPromocode());

        assertNotNull(actual.getCategory());
        assertNotNull(actual.getVendor());
        assertNotNull(actual.getLocations());

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
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getPromocode(), actual.getPromocode());

        assertNotNull(actual.getCategory());
        assertNotNull(actual.getVendor());
        assertNotNull(actual.getLocations());

        assertEquals(expected.getCategoryId(), actual.getCategory().getId());
        assertEquals(expected.getVendorId(), actual.getVendor().getId());
        assertEquals(expected.getLocationIds().size(), actual.getLocations().size());
        assertEquals(expected.getTagIds().size(), actual.getTags().size());
    }

    private void matchOnePure(Discount expected, DiscountResponseVO actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getShortDescription(), actual.getShortDescription());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getFlatAmount(), actual.getFlatAmount());
        assertEquals(expected.getPercentage(), actual.getPercentage());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
    }

    private void matchAll(List<Discount> expected, List<DiscountResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }

    private void matchAllPure(List<Discount> expected, List<DiscountResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOnePure(expected.get(i), actual.get(i));
        }
    }
}
