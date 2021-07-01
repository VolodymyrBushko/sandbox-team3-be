package com.exadel.discountwebapp.user.controller;

import com.exadel.discountwebapp.user.service.UserDiscountService;
import com.exadel.discountwebapp.user.vo.UserDiscountRequestVO;
import com.exadel.discountwebapp.user.vo.UserDiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-discount")
public class UserDiscountController {

    private final UserDiscountService userDiscountService;

//    @PostMapping
//    public UserDiscountResponseVO create(@Valid @RequestBody UserDiscountRequestVO request) {
//        return userdiscountService.create(request);
//    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody UserDiscountRequestVO request) {
        return userDiscountService.create(request);
    }

    @GetMapping("/users/{discountId}")
    public List<UserDiscountResponseVO> findAllUsersByDiscountId(@PathVariable Long discountId) {
        return userDiscountService.findAllUsersByDiscountId(discountId);
    }

    @GetMapping("/discounts/{userId}")
    public List<UserDiscountResponseVO> findAllDiscountsByUserId(@PathVariable Long userId) {
        return userDiscountService.findAllDiscountsByUserId(userId);
    }
}
