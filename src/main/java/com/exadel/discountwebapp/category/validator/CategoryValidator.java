package com.exadel.discountwebapp.category.validator;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryRequestVO;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyUsedException;
import com.exadel.discountwebapp.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public void checkDuplicateTag(Category category, List<Tag> newTags) {
        newTags.forEach(e -> {
            if (category.getTags().contains(e)) {
                throw new EntityAlreadyExistsException(Tag.class, "name", e.getName());
            }
        });
    }

    public void checkTagAlreadyUsedInDiscount(Category category, List<Long> tagIds) {
        List<Discount> discounts = category.getDiscounts();

        discounts.forEach(d -> {
            Optional<Long> usedId = d.getTags()
                    .stream()
                    .mapToLong(Tag::getId)
                    .boxed()
                    .filter(tagIds::contains)
                    .findFirst();

            if (usedId.isPresent()) {
                throw new EntityAlreadyUsedException(Tag.class, "id", usedId.get());
            }
        });
    }
}
