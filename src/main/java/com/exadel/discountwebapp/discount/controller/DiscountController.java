package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.RequiredArgsConstructor;
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
    public List<DiscountResponseVO> findAll(@RequestParam(value = "query", required = false) String query) {
        if (query != null && query.trim().length() > 0) {

            String regexp = "([a-zA-Z]+)(:|<|>)(%?\\w+%?|\\d{4}-\\d{2}-\\d{2}),";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(query.trim() + ",");

            while (matcher.find()) {
                System.out.println("1: " + matcher.group(1));
                System.out.println("2: " + matcher.group(2));
                System.out.println("3: " + matcher.group(3));
            }
        }
        return discountService.findAll();
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
