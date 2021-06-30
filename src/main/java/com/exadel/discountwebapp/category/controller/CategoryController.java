package com.exadel.discountwebapp.category.controller;

import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<CategoryResponseVO> findAll() {
        return categoryService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public CategoryResponseVO findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public CategoryResponseVO create(@Valid @RequestBody CategoryRequestVO request) {
        return categoryService.create(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public CategoryResponseVO update(@PathVariable Long id, @Valid @RequestBody CategoryRequestVO request) {
        return categoryService.update(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
