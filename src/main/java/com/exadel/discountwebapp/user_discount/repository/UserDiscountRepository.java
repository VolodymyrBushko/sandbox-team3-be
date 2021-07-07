package com.exadel.discountwebapp.user_discount.repository;

import com.exadel.discountwebapp.user_discount.entity.UserDiscount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, UserDiscount.UserDiscountId> {
}
