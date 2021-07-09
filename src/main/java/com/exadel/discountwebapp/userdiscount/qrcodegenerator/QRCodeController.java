package com.exadel.discountwebapp.userdiscount.qrcodegenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qrcode")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @GetMapping("/{userId}/{discountId}")
    public QRCodeResponseVO getQRCodeViewPage(@PathVariable Long userId, @PathVariable Long discountId) {
        return qrCodeService.qrCodeViewPage(userId, discountId);
    }
}