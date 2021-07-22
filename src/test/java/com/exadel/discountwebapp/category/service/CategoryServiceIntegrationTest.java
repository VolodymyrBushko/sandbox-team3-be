package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        var expected = Lists.newArrayList(expectedIter);
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

        return CategoryRequestVO.builder()
                .title(title)
                .build();
    }

    private void matchOne(Category expected, CategoryResponseVO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    private void matchOne(CategoryRequestVO expected, CategoryResponseVO actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    private void matchAll(List<Category> expected, List<CategoryResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }
}
