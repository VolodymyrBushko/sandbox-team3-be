package com.exadel.discountwebapp.discount.repository;

import com.exadel.discountwebapp.discount.entity.Discount;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount, Long> {
}
