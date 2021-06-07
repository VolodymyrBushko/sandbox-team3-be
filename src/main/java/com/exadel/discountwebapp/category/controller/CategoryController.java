package com.exadel.discountwebapp.category.controller;

import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseVO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseVO findById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryResponseVO create(@RequestBody CategoryRequestVO request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponseVO update(@PathVariable long id, @RequestBody CategoryRequestVO request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
