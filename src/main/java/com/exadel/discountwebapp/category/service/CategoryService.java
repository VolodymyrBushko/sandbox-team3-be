package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<CategoryResponseVO> findAll() {
        List<CategoryResponseVO> response = new ArrayList<>();
        categoryRepository.findAll().forEach(e -> response.add(categoryMapper.toVO(e)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public CategoryResponseVO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id: " + id));
        return categoryMapper.toVO(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO create(CategoryRequestVO request) {
        Category category = categoryMapper.toEntity(request);
        categoryRepository.save(category);
        return categoryMapper.toVO(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO update(Long id, CategoryRequestVO request) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Could not find category with id: " + id));
        categoryMapper.updateEntity(request, category);
        categoryRepository.save(category);
        return categoryMapper.toVO(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id: " + id));
    }
}
