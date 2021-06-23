package com.exadel.discountwebapp.tag.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.service.CategoryService;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final CategoryService categoryService;

    @Autowired
    public TagMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TagResponseVO toVO(Tag entity) {
        TagResponseVO response = modelMapper.map(entity, TagResponseVO.class);
        return response;
    }

    public Tag toEntity(TagRequestVO request) {
        Tag tag = modelMapper.map(request, Tag.class);
        provideTagDependencies(request, tag);
        return tag;
    }

    public void updateEntity(TagRequestVO request, Tag tag) {
        provideTagDependencies(request, tag);
        modelMapper.map(request, tag);
    }

    private void provideTagDependencies(TagRequestVO request, Tag tag) {
        Category category = categoryService.findCategoryById(request.getCategoryId());
        tag.setCategory(category);
    }
}