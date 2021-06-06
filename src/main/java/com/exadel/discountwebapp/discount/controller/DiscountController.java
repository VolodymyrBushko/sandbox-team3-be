package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.RequestDiscountVO;
import com.exadel.discountwebapp.discount.vo.ResponseDiscountVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
@AllArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<ResponseDiscountVO> findAll() {
        return discountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseDiscountVO findById(@PathVariable long id) {
        return discountService.findById(id);
    }

    @PostMapping
    public ResponseDiscountVO create(@RequestBody RequestDiscountVO request) {
        return discountService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseDiscountVO update(@PathVariable long id, @RequestBody RequestDiscountVO request) {
        return discountService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        discountService.deleteById(id);
    }
}
