package com.exadel.discountwebapp.statistics.extendedvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedDiscountVO {
    private String title;
    private String shortDescription;
    private String description;
    private String promocode;
    private BigDecimal percentage;
    private BigDecimal flatAmount;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String vendorTitle;
    private String categoryTitle;
    private Long viewNumber;
}
