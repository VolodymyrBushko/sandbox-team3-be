package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(long id);
}
