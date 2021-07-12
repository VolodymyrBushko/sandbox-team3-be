package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.exception.exception.client.IncorrectFilterInputException;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsAndSortByTitleWithDirAsc() {
        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);
        expected.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));

        var sortField = "title";
        var sortDir = Sort.Direction.ASC;
        var sort = Sort.by(sortDir, sortField);

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount, sort);
        var actual = vendorService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsAndSortByTitleWithDirDesc() {
        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter);
        expected.sort((a, b) -> b.getTitle().compareTo(a.getTitle()));

        var sortField = "title";
        var sortDir = Sort.Direction.DESC;
        var sort = Sort.by(sortDir, sortField);

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount, sort);
        var actual = vendorService.findAll(null, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereIdLessThanTwo() {
        var id = 2L;
        var query = "id<" + id;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getId() < id).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereIdGreaterThanTwo() {
        var id = 2L;
        var query = "id>" + id;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getId() > id).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereCreatedDateBetweenTwoDates() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        var firstDate = LocalDateTime.parse("2021-06-06 17:22:21", formatter);
        var secondDate = LocalDateTime.parse("2023-06-06 17:22:21", formatter);
        var query = String.format("created>%s;created<%s", firstDate, secondDate);

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getCreated().isAfter(firstDate) && e.getCreated().isBefore(secondDate)).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereTitleEqualsSportLife() {
        var title = "sport life";
        var query = "title:" + title;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereTitleStartsWithSport() {
        var title = "sport";
        var query = "title:*" + title;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getTitle().toLowerCase().startsWith(title)).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereTitleEndsWithLife() {
        var title = "life";
        var query = "title*:" + title;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getTitle().toLowerCase().endsWith(title)).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereDescriptionContainsPizza() {
        var description = "pizza";
        var query = "description*:*" + description;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getDescription().toLowerCase().contains(description)).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereCityEqualsLviv() {
        var city = "lviv";
        var query = "location.city:" + city;

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream().filter(e -> e.getLocation().getCity().equalsIgnoreCase(city)).collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldFindAllVendorsWhereTitleStartsWithSportAndDescriptionContainsCasualAndCityEqualsKyiv() {
        var title = "sport";
        var description = "casual";
        var city = "kyiv";
        var query = String.format("title:*%s;description*:*%s;location.city:%s", title, description, city);

        var expectedIter = vendorRepository.findAll();
        var expected = Lists.newArrayList(expectedIter)
                .stream()
                .filter(e -> e.getTitle().toLowerCase().startsWith(title) &&
                        e.getDescription().toLowerCase().contains(description) &&
                        e.getLocation().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAll(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfFieldDoesNotExistInVendor() {
        var field = "I don't exist";
        var query = field + ":some value";

        var pageable = PageRequest.of(0, 20);

        assertThrows(IncorrectFilterInputException.class, () -> {
            vendorService.findAll(query, pageable);
        });
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

    @Test
    void shouldFindAllVendorsWhereTitleContainsSportOrPizza() {
        var firstTitle = "sport";
        var secondTitle = "pizza";

        var query = String.format("title?*:*%s;title?*:*%s;", firstTitle, secondTitle);

        var firstVendor = Vendor.builder()
                .id(1L)
                .title("Sport Life")
                .description("Sport Life - a chain of casual fitness centers")
                .imageUrl("sport_life_image_1.jsp")
                .email("sprort_life@com.ua")
                .created(LocalDateTime.parse("2021-12-06T17:22:21"))
                .modified(LocalDateTime.parse("2021-12-06T17:22:21"))
                .build();

        var secondVendor = Vendor.builder()
                .id(2L)
                .title("Domino`s Pizza")
                .description("Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960")
                .imageUrl("dominos.com_image_1.jsp")
                .email("dominos@gmail.com")
                .created(LocalDateTime.parse("2022-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2022-06-06T17:22:21"))
                .build();

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);

        var expected = List.of(firstVendor, secondVendor);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAllPure(expected, actual);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void shouldFindAllVendorsWhereDescriptionContainsTravelOrPizzaAndEmailEndsWithCom() {
        var firstDesc = "travel";
        var secondDesc = "pizza";
        var emailEnds = "com";

        var query = String.format("description?*:*%s;description?*:*%s;email*:%s",
                firstDesc, secondDesc, emailEnds);

        var firstVendor = Vendor.builder()
                .id(2L)
                .title("Domino`s Pizza")
                .description("Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960")
                .imageUrl("dominos.com_image_1.jsp")
                .email("dominos@gmail.com")
                .created(LocalDateTime.parse("2022-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2022-06-06T17:22:21"))
                .build();

        var secondVendor = Vendor.builder()
                .id(3L)
                .title("TUI")
                .description("TUI AG - travel and tourism company")
                .imageUrl("tui_image_1.jsp")
                .email("tuigroup@gmail.com")
                .created(LocalDateTime.parse("2023-06-06T17:22:21"))
                .modified(LocalDateTime.parse("2023-06-06T17:22:21"))
                .build();

        var vendorCount = (int) vendorRepository.count();
        var pageable = PageRequest.of(0, vendorCount);

        var expected = List.of(firstVendor, secondVendor);
        var actual = vendorService.findAll(query, pageable).getContent();

        matchAllPure(expected, actual);
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

    private void matchOnePure(Vendor expected, VendorResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

    private void matchAll(List<Vendor> expected, List<VendorResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }

    private void matchAllPure(List<Vendor> expected, List<VendorResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOnePure(expected.get(i), actual.get(i));
        }
    }
}
