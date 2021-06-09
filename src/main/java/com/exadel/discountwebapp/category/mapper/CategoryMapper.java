package com.exadel.discountwebapp.category.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryResponseVO toVO(Category category) {
        return modelMapper.map(category, CategoryResponseVO.class);
    }

    public Category toEntity(CategoryRequestVO request) {
        return modelMapper.map(request, Category.class);
    }

    public void updateEntity(CategoryRequestVO request, Category category) {
        modelMapper.map(request, category);
    }
}
