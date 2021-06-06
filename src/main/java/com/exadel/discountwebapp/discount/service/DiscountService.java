package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;

import java.util.List;

public interface DiscountService {

    List<Discount> findAll();

    Discount findById(long id);

    Discount save(Discount discount);

    Discount update(Discount discount);

    void deleteById(long id);
}
