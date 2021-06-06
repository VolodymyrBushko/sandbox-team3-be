package com.exadel.discountwebapp.discount.service.impl;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<Discount> findAll() {
        List<Discount> discounts = new ArrayList<>();
        discountRepository.findAll().forEach(discounts::add);
        return discounts;
    }

    @Override
    public Discount findById(long id) {
        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public Discount save(Discount discount) {
        if (discount != null && discount.getId() <= 0) {
            return discountRepository.save(discount);
        }
        return null;
    }

    @Override
    public Discount update(Discount discount) {
        if (discount != null && discount.getId() > 0) {
            return discountRepository.save(discount);
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        discountRepository.deleteById(id);
    }
}
