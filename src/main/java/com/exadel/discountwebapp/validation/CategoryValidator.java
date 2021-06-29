package com.exadel.discountwebapp.validation;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public void validate(CategoryRequestVO request) {
        checkDuplicateTitle(request);
    }

    public void checkDuplicateTitle(CategoryRequestVO request) {
        categoryRepository.findByTitle(request.getTitle())
                .ifPresent(category -> {
                    throw new EntityAlreadyExistsException(Category.class, "title", category.getTitle());
                });
    }
}
