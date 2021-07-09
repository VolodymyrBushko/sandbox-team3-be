package com.exadel.discountwebapp.userdiscount.qrcodegenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String promocode;

    private LocalDateTime discountStartDate;
    private LocalDateTime discountExpirationDate;

}
