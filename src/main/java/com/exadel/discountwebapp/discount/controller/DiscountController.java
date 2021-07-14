package com.exadel.discountwebapp.discount.controller;

import com.exadel.discountwebapp.discount.service.DiscountService;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public Page<DiscountResponseVO> findAll(@RequestParam(value = "query", defaultValue = "", required = false) String query,
                                            @PageableDefault(sort = {"expirationDate"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return discountService.findAll(query, pageable);
    }

    @GetMapping("/{id}")
    public DiscountResponseVO findById(@PathVariable Long id) {
        return discountService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public DiscountResponseVO create(@Valid @RequestBody DiscountRequestVO request) {
        return discountService.create(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public DiscountResponseVO update(@PathVariable Long id, @Valid @RequestBody DiscountRequestVO request) {
        return discountService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        discountService.deleteById(id);
    }

    @PostMapping("/favorites")
    public ResponseEntity<Void> addDiscountToFavorites(@RequestParam(value = "userId") Long userId, @RequestParam(value = "discountId") Long discountId) {
        discountService.addDiscountToFavorites(userId, discountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<Void> deleteDiscountFromFavorites(@RequestParam(value = "userId") Long userId, @RequestParam(value = "discountId") Long discountId) {
        discountService.deleteDiscountFromFavorites(userId, discountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}