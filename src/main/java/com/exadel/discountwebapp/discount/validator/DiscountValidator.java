package com.exadel.discountwebapp.discount.validator;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyUsedException;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountValidator {

    private final DiscountRepository discountRepository;

    public void checkIfDiscountIsPresentById(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new EntityNotFoundException(Discount.class, "id", id);
        }
    }

    public void checkFavoritesToAdd(Discount discount, User user) {
        if (discount.getFavoriteUsers().contains(user)) {
            throw new EntityAlreadyUsedException(Discount.class, "id", discount.getId());
        }
    }

    public void checkFavoritesToDelete(Discount discount, User user) {
        if (!(discount.getFavoriteUsers().contains(user))) {
            throw new EntityAlreadyUsedException(Discount.class, "id", discount.getId());
        }
    }
}