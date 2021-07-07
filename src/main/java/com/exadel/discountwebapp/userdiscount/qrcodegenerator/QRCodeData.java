package com.exadel.discountwebapp.userdiscount.qrcodegenerator;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QRCodeData {
    private String userFirstName;
    private String userLastName;
    private String vendorTitle;
    private String vendorEmail;
    private String discountTitle;
    private String discountPromocode;
}