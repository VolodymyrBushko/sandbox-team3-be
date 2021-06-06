package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.user.entity.UserDiscount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, Long> {

}
