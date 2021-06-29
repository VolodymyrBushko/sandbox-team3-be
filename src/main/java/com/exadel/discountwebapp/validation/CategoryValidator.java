package com.exadel.discountwebapp.validation;

import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.exception.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public void validate(CategoryRequestVO request) {
        checkDuplicateTitle(request);
    }

    public void checkDuplicateTitle(@NotNull CategoryRequestVO request) {
        if (categoryRepository.existsByTitle(request.getTitle()))
            throw new EntityAlreadyExistsException(
                    String.format("Category with title \"%s\" already exist", request.getTitle()));
    }
}
