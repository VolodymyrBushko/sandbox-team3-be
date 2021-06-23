package com.exadel.discountwebapp.validation;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.exception.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public void validate(CategoryRequestVO request) {
        checkDuplicateTitle(request);
    }

    public boolean checkDuplicateTitle(CategoryRequestVO request) {
        Optional<Category> title = categoryRepository.findByTitle(request.getTitle());

        if (title.isPresent()) {
            throw new EntityAlreadyExistsException("Category with title \"" + title.get().getTitle() + "\" already exist");
        } else {
            return false;
        }
    }
}
