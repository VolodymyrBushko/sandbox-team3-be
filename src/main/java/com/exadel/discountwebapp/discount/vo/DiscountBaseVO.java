package com.exadel.discountwebapp.discount.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class DiscountBaseVO implements Serializable {

    @NotBlank
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String shortDescription;

    @NotBlank
    @Size(max = 510)
    private String description;

    @Size(max = 510)
    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$")
    private String imageUrl;

    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal flatAmount;

    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 3, fraction = 1)
    private BigDecimal percentage;

    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    @Future
    private LocalDateTime expirationDate;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Integer perUser;

    @NotNull
    @Positive
    private Long categoryId;

    @NotNull
    @Positive
    private Long vendorId;
}
