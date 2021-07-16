package com.exadel.discountwebapp.category.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.validator.CategoryTagsValidator;
import com.exadel.discountwebapp.category.validator.CategoryTitleValidator;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.mapper.TagMapper;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final TagMapper tagMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    private final CategoryTagsValidator categoryTagsValidator;
    private final CategoryTitleValidator categoryTitleValidator;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<CategoryResponseVO> findAll() {
        List<CategoryResponseVO> response = new ArrayList<>();
        categoryRepository.findAll().forEach(e -> response.add(categoryMapper.toVO(e)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public CategoryResponseVO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "id", id));
        return categoryMapper.toVO(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO create(CategoryRequestVO request) {
        categoryTitleValidator.validate(request);
        Category category = categoryMapper.toEntity(request);
        categoryRepository.save(category);
        return categoryMapper.toVO(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO update(Long id, CategoryRequestVO request) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(Category.class, "id", id));

        if (!category.getTitle().equals(request.getTitle())) {
            categoryTitleValidator.validate(request);
        }

        categoryMapper.updateEntity(request, category);
        categoryRepository.save(category);
        return categoryMapper.toVO(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO addTags(Long categoryId, List<TagRequestVO> tagRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "id", categoryId));

        List<Tag> newTags = tagRequest
                .stream()
                .map(e -> tagMapper.toEntity(e, category.getId()))
                .collect(Collectors.toList());

        categoryTagsValidator.checkDuplicateTag(category, newTags);

        category.getTags().addAll(newTags);
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toVO(updatedCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResponseVO deleteTags(Long categoryId, List<Long> tagIds) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "id", categoryId));

        categoryTagsValidator.checkTagAlreadyUsedInDiscount(category, tagIds);

        category.getTags().removeIf(e -> tagIds.contains(e.getId()));
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toVO(updatedCategory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
