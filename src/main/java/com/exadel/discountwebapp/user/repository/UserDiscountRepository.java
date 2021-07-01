package com.exadel.discountwebapp.user.repository;

import com.exadel.discountwebapp.user.entity.UserDiscount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDiscountRepository extends CrudRepository<UserDiscount, UserDiscount.UserDiscountId> {
    List<UserDiscount> findAllByDiscount_Id(Long discountId);
    List<UserDiscount> findAllByUser_Id(Long userId);
}
