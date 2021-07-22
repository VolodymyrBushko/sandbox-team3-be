package com.exadel.discountwebapp.category.validator;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyUsedException;
import com.exadel.discountwebapp.tag.entity.Tag;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
public class CategoryTagsValidator {

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
                throw new EntityAlreadyUsedException(Tag.class, "Tag can't be deleted, because it's already used");
            }
        });
    }
}
