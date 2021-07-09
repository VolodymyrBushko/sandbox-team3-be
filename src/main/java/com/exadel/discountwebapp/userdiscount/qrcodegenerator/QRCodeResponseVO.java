package com.exadel.discountwebapp.userdiscount.qrcodegenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
public class QRCodeResponseVO {
    private String firstName;
    private String lastName;
    private String imageUrl;

    private String vendorTitle;
    private String vendorEmail;

    private String discountTitle;
    private String promocode;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountExpirationDate;

}
