package com.exadel.discountwebapp.category.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CategoryMapper() {
        configureModelMapper(modelMapper);
    }

    public CategoryResponseVO toVO(Category category) {
        return modelMapper.map(category, CategoryResponseVO.class);
    }

    public Category toEntity(CategoryRequestVO request) {
        return modelMapper.map(request, Category.class);
    }

    public void updateEntity(CategoryRequestVO request, Category category) {
        modelMapper.map(request, category);
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
