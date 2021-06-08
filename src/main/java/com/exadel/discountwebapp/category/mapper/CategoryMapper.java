package com.exadel.discountwebapp.category.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseVO toVO(Category category) {
        return CategoryResponseVO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .imageUrl(category.getImageUrl())
                .build();
    }

    public Category toEntity(CategoryRequestVO request) {
        return Category.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public void updateEntity(CategoryRequestVO request, Category category) {
        category.setTitle(request.getTitle());
        category.setImageUrl(request.getImageUrl());
    }
}
