package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.exception.EntityNotFoundException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/category-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldGetCategoryById() {
        var id = 1L;
        var expected = categoryRepository.findById(1L).get();
        var actual = categoryService.findById(id);

        matchOne(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfNoCategoryFoundById() {
        var id = 3L;
        assertThrows(EntityNotFoundException.class, () -> {
            categoryService.findById(id);
        });
    }

    @Test
    void shouldGetAllCategories() {
        var expectedIter = categoryRepository.findAll();
        var expected = iterableToList(expectedIter);
        var actual = categoryService.findAll();

        matchAll(expected, actual);
    }

    @Test
    void shouldCreateCategory() {
        var categoryRequest = createCategoryRequest();
        var categoryResponse = categoryService.create(categoryRequest);

        assertNotNull(categoryResponse);
        assertNotNull(categoryResponse.getId());

        matchOne(categoryRequest, categoryResponse);
    }

    @Test
    void shouldCategoryUpdateById() {
        var id = 1L;
        var categoryRequest = createCategoryRequest();
        var categoryResponse = categoryService.update(id, categoryRequest);

        assertNotNull(categoryResponse);
        assertEquals(categoryResponse.getId(), id);

        matchOne(categoryRequest, categoryResponse);
    }

    private CategoryRequestVO createCategoryRequest() {
        var title = "title";
        var imageUrl = "http://localhost/images/img.png";

        return CategoryRequestVO.builder()
                .title(title)
                .imageUrl(imageUrl)
                .build();
    }

    private void matchOne(Category expected, CategoryResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
    }

    private void matchOne(CategoryRequestVO expected, CategoryResponseVO actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
    }

    private void matchAll(List<Category> expected, List<CategoryResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }

    private <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
