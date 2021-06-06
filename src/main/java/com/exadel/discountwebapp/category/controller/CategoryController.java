package com.exadel.discountwebapp.category.controller;

import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.category.vo.RequestCategoryVO;
import com.exadel.discountwebapp.category.vo.ResponseCategoryVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<ResponseCategoryVO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseCategoryVO findById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseCategoryVO create(@RequestBody RequestCategoryVO request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseCategoryVO update(@PathVariable long id, @RequestBody RequestCategoryVO request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
