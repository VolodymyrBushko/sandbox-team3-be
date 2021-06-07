package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseVO> findAll() {
        List<CategoryResponseVO> response = new ArrayList<>();
        categoryRepository.findAll().forEach(e -> response.add(categoryMapper.toVO(e)));
        return response;
    }

    public CategoryResponseVO findById(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? categoryMapper.toVO(category) : null;
    }

    public CategoryResponseVO create(CategoryRequestVO request) {
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toVO(categoryRepository.save(category));
    }

    public CategoryResponseVO update(long id, CategoryRequestVO request) {
        Category oldCategory = categoryRepository.findById(id).orElse(null);
        if (oldCategory != null) {
            Category newCategory = categoryMapper.toEntity(request);
            newCategory.setId(id);
            return categoryMapper.toVO(categoryRepository.save(newCategory));
        }
        return null;
    }

    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
