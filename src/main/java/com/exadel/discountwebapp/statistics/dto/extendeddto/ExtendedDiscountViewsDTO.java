package com.exadel.discountwebapp.statistics.dto.extendeddto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedDiscountViewsDTO {
    private String title;
    private String shortDescription;
    private String description;
    private String promocode;
    private BigDecimal percentage;
    private BigDecimal flatAmount;
    private LocalDateTime created;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private String vendorTitle;
    private String categoryTitle;
    private Long viewNumber;
}
