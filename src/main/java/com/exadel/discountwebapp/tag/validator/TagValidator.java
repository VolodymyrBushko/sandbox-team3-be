package com.exadel.discountwebapp.tag.validator;

import com.exadel.discountwebapp.exception.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagValidator {

    private final TagRepository tagRepository;

    public void checkUniqueTag(Tag tag) {
        if (tagRepository.existsByCategoryAndName(tag.getCategory(), tag.getName())) {
            throw new EntityAlreadyExistsException("Such tag already exists");
        }
    }

    public void checkIfTagIsPresentById(Long id) {
        if (tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Could not find tag with id " + id);
        }
    }
}
