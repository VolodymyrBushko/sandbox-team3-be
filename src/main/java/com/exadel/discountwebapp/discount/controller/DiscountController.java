package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.filter.SpecificationBuilder;
import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private SpecificationBuilder<Discount> specificationBuilder = new SpecificationBuilder<>();

    @GetMapping
    public List<DiscountResponseVO> findAll(@RequestParam(value = "query", defaultValue = "", required = false) String query, Pageable pageable) {
        Specification<Discount> specification = specificationBuilder.fromQuery(query);
        return discountService.findAll(specification, pageable);
    }

    @GetMapping("/{id}")
    public DiscountResponseVO findById(@PathVariable Long id) {
        return discountService.findById(id);
    }

    @PostMapping
    public DiscountResponseVO create(@Valid @RequestBody DiscountRequestVO request) {
        return discountService.create(request);
    }

    @PutMapping("/{id}")
    public DiscountResponseVO update(@PathVariable Long id, @Valid @RequestBody DiscountRequestVO request) {
        return discountService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        discountService.deleteById(id);
    }
}
