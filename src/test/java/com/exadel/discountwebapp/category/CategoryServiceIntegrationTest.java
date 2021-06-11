package com.exadel.discountwebapp.category;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.junit.jupiter.api.Test;

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
        var expected = categoryRepository.findById(id).get();
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
        var actual = (List<Category>) categoryRepository.findAll();
        var expected = categoryService.findAll();

        matchAll(actual, expected);
    }

    @Test
    void shouldCreateCategory() {
        var title = "test";
        var imageUrl = "http://localhost/images/image.png";

        var categoryRequest = createCategoryRequest(title, imageUrl);
        var categoryResponse = categoryService.create(categoryRequest);

        assertNotNull(categoryResponse);
        assertNotNull(categoryResponse.getId());
        assertEquals(categoryResponse.getTitle(), categoryRequest.getTitle());
        assertEquals(categoryResponse.getImageUrl(), categoryRequest.getImageUrl());
    }

    @Test
    void shouldCategoryUpdateById() {
        var id = 1L;
        var title = "test";
        var imageUrl = "http://localhost/images/image.png";

        var categoryRequest = createCategoryRequest(title, imageUrl);
        var categoryResponse = categoryService.update(id, categoryRequest);

        assertNotNull(categoryResponse);
        assertEquals(categoryResponse.getId(), id);
        assertEquals(categoryResponse.getTitle(), categoryRequest.getTitle());
        assertEquals(categoryResponse.getImageUrl(), categoryRequest.getImageUrl());
    }

    @Test
    void shouldCategoryDeleteById() {
        var id = 1L;
        var beforeDelete = categoryRepository.findById(id).orElse(null);
        assertNotNull(beforeDelete);
        categoryService.deleteById(id);
        var afterDelete = categoryRepository.findById(id).orElse(null);
        assertNull(afterDelete);
    }

    private CategoryRequestVO createCategoryRequest(String title, String imageUrl) {
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

    private void matchAll(List<Category> expected, List<CategoryResponseVO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            matchOne(expected.get(i), actual.get(i));
        }
    }
}
