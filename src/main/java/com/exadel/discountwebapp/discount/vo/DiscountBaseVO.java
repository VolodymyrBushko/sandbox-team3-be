package com.exadel.discountwebapp.discount.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class DiscountBaseVO implements Serializable {

    private String title;
    private String shortDescription;
    private String description;
    private String imageUrl;
    private BigDecimal flatAmount;
    private BigDecimal percentage;
    private BigDecimal price;
    private LocalDateTime startDate;
    private LocalDateTime expirationDate;
    private Integer quantity;
    private Integer perUser;

    private Long categoryId;
    private Long vendorId;
}
