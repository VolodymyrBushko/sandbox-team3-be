package com.exadel.discountwebapp.discount.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class DiscountBaseVO implements Serializable {

    protected String title;
    protected String shortDescription;
    protected String description;
    protected String imageUrl;
    protected BigDecimal flatAmount;
    protected BigDecimal price;
    protected LocalDateTime startDate;
    protected LocalDateTime expirationDate;

    protected int percentage;
    protected int quantity;
    protected int perUser;
}
