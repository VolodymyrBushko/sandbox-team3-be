package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
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
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? categoryMapper.toVO(category) : null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO create(CategoryRequestVO request) {
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toVO(categoryRepository.save(category));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO update(Long id, CategoryRequestVO request) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryMapper.updateEntity(request, category);
            return categoryMapper.toVO(categoryRepository.save(category));
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
