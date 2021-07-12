package com.exadel.discountwebapp.userdiscount.repository;

import com.exadel.discountwebapp.userdiscount.entity.UserDiscount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, UserDiscount.UserDiscountId> {
}
