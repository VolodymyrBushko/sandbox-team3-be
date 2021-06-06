package com.exadel.discountwebapp.category.service.impl;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        if (category != null && category.getId() <= 0) {
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public Category update(Category category) {
        if (category != null && category.getId() > 0) {
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
