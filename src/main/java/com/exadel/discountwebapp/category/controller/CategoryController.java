package com.exadel.discountwebapp.category.controller;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping({"", "/"})
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping({"", "/"})
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
