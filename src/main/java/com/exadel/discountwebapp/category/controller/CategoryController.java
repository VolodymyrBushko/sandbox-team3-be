package com.exadel.discountwebapp.category.controller;

import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseVO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseVO findById(@NotNull(message = "Id should not be null") @Positive(message = "Id must be a positive number") @PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryResponseVO create(@Valid @RequestBody CategoryRequestVO request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponseVO update(
            @NotNull(message = "Id should not be null") @Positive(message = "Id must be a positive number") @PathVariable Long id,
            @Valid @RequestBody CategoryRequestVO request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@NotNull(message = "Id should not be null") @Positive(message = "Id must be a positive number") @PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
