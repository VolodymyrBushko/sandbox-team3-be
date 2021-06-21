package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.filter.DiscountSpecificationBuilder;
import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<DiscountResponseVO> findAll(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortField", required = false, defaultValue = "") String sortField,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "page", required = false, defaultValue = "0") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize) {

        Sort sort = createSort(sortField, sortDirection);
        Pageable pageable = createPageable(parseInt(page), parseInt(pageSize), sort);
        Specification<Discount> specification = createSpecification(query);

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

    private Specification<Discount> createSpecification(String query) {
        if (query == null || query.trim().length() == 0) {
            return null;
        }

        String regexp = "([a-zA-Z]+)(:|<|>)([^(;).]+);";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(query.trim() + ";");
        DiscountSpecificationBuilder specificationBuilder = new DiscountSpecificationBuilder();

        while (matcher.find()) {
            specificationBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        return specificationBuilder.build();
    }

    private Sort createSort(String sortField, String sortDirection) {
        Direction direction = sortDirection.equalsIgnoreCase("DESC")
                ? Direction.DESC
                : Direction.ASC;

        return (sortField == null || sortField.trim().length() == 0)
                ? null
                : Sort.by(direction, sortField);
    }

    private Pageable createPageable(int page, int pageSize, Sort sort) {
        return sort != null
                ? PageRequest.of(page, pageSize, sort)
                : PageRequest.of(page, pageSize);
    }

    public int parseInt(String s) {
        return s.matches("-?\\d+")
                ? Integer.parseInt(s)
                : 0;
    }
}
