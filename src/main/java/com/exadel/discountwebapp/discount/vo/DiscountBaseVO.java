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

    protected long categoryId;
    protected long vendorId;
}
