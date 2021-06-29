package com.exadel.discountwebapp.tag.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.mapper.TagMapper;
import com.exadel.discountwebapp.tag.repository.TagRepository;
import com.exadel.discountwebapp.tag.vo.TagRequestVO;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import com.exadel.discountwebapp.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public TagResponseVO findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find tag with id: " + id));
        TagResponseVO response = tagMapper.toVO(tag);
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<TagResponseVO> findAll() {
        List<TagResponseVO> response = new ArrayList<>();
        tagRepository.findAll().forEach(tag -> response.add(tagMapper.toVO(tag)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TagResponseVO create(TagRequestVO request) {
        Tag tag = tagMapper.toEntity(request);
        tagValidator.checkUniqueTag(tag);
        tagRepository.save(tag);
        return tagMapper.toVO(tag);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public TagResponseVO update(Long id, TagRequestVO request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("Could not find tag with id " + id)));
        tagMapper.updateEntity(request, tag);
        tagRepository.save(tag);
        return tagMapper.toVO(tag);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        tagValidator.checkIfTagIsPresentById(id);
        tagRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<TagResponseVO> findAllTagsByCategoryId(Long categoryId) {
        List<TagResponseVO> response = new ArrayList<>();
        tagRepository.findAllByCategory_Id(categoryId).forEach(tag -> response.add(tagMapper.toVO(tag)));
        if(!response.isEmpty()) {
            return response;
        } else {
            return Collections.emptyList();
        }
    }
}