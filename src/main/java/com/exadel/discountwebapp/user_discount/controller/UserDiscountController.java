package com.exadel.discountwebapp.user_discount.controller;

import com.exadel.discountwebapp.user_discount.service.UserDiscountService;
import com.exadel.discountwebapp.user_discount.vo.UserDiscountRequestVO;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-discount")
public class UserDiscountController {

    private final UserDiscountService userDiscountService;

    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] create(@Valid @RequestBody UserDiscountRequestVO request) throws IOException, WriterException {
        return userDiscountService.create(request);
    }

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] findQRCodeByUserDiscountId(@Valid @RequestBody UserDiscountRequestVO request) {
        return userDiscountService.findQRCodeByUserDiscountId(request);
    }
}
