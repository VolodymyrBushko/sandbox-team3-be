package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping({"", "/"})
    public List<Discount> findAll() {
        return discountService.findAll();
    }

    @GetMapping("/{id}")
    public Discount findById(@PathVariable long id) {
        return discountService.findById(id);
    }

    @PostMapping({"", "/"})
    public Discount save(@RequestBody Discount discount) {
        return discountService.save(discount);
    }

    @PutMapping({"", "/"})
    public Discount update(@RequestBody Discount discount) {
        return discountService.update(discount);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        discountService.deleteById(id);
    }
}
