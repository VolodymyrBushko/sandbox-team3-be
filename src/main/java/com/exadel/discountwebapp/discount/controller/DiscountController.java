package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/discount")
@RequiredArgsConstructor
@Validated
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<DiscountResponseVO> findAll() {
        return discountService.findAll();
    }

    @GetMapping("/{id}")
    public DiscountResponseVO findById(@NotNull(message = "Id should not be null") @Positive(message = "Id must be a positive number") @PathVariable Long id) {
        return discountService.findById(id);
    }

    @PostMapping
    public DiscountResponseVO create(@Valid @RequestBody DiscountRequestVO request) {
        return discountService.create(request);
    }

    @PutMapping("/{id}")
    public DiscountResponseVO update(
            @NotNull(message = "Id should not be null") @Positive(message = "Id must be a positive number") @PathVariable Long id,
            @Valid @RequestBody DiscountRequestVO request) {
        return discountService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@NotNull(message = "Id should not be null") @Positive(message = "Id must be a positive number") @PathVariable Long id) {
        discountService.deleteById(id);
    }
}
