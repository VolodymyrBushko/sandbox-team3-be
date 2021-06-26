package com.exadel.discountwebapp.tag.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TagMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(createSkipPropertyMap());
    }

    public TagResponseVO toVO(Tag tag) {
        TagResponseVO response = modelMapper.map(tag, TagResponseVO.class);
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
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find category with id: " + request.getCategoryId()));

        tag.setCategory(category);
    }

    private PropertyMap<TagRequestVO, Tag> createSkipPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setCategory(null);
                skip().setDiscounts(null);
            }
        };
    }
}