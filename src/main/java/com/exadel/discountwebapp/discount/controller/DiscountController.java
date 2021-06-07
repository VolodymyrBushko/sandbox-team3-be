package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
@AllArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<DiscountResponseVO> findAll() {
        return discountService.findAll();
    }

    @GetMapping("/{id}")
    public DiscountResponseVO findById(@PathVariable long id) {
        return discountService.findById(id);
    }

    @PostMapping
    public DiscountResponseVO create(@RequestBody DiscountRequestVO request) {
        return discountService.create(request);
    }

    @PutMapping("/{id}")
    public DiscountResponseVO update(@PathVariable long id, @RequestBody DiscountRequestVO request) {
        return discountService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        discountService.deleteById(id);
    }
}
