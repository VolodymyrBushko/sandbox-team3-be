package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.RequestCategoryVO;
import com.exadel.discountwebapp.category.vo.ResponseCategoryVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<ResponseCategoryVO> findAll() {
        List<ResponseCategoryVO> response = new ArrayList<>();
        categoryRepository.findAll().forEach(e -> response.add(ResponseCategoryVO.fromCategory(e)));
        return response;
    }

    public ResponseCategoryVO findById(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? ResponseCategoryVO.fromCategory(category) : null;
    }

    public ResponseCategoryVO create(RequestCategoryVO request) {
        Category category = RequestCategoryVO.toCategory(request);
        return ResponseCategoryVO.fromCategory(categoryRepository.save(category));
    }

    public ResponseCategoryVO update(long id, RequestCategoryVO request) {
        Category oldCategory = categoryRepository.findById(id).orElse(null);
        if (oldCategory != null) {
            Category newCategory = RequestCategoryVO.toCategory(request);
            newCategory.setId(id);
            return ResponseCategoryVO.fromCategory(categoryRepository.save(newCategory));
        }
        return null;
    }

    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
