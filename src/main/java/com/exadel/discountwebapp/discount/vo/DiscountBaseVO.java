package com.exadel.discountwebapp.discount.vo;

import com.exadel.discountwebapp.discount.validator.FlatAmountOrPercentageFieldsNotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@FlatAmountOrPercentageFieldsNotNull
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
    private String imageUrl;

    @DecimalMin(value = "0.0")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal flatAmount;

    @DecimalMin(value = "0.0")
    @Digits(integer = 3, fraction = 1)
    private BigDecimal percentage;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    @Future
    private LocalDateTime expirationDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 2, max = 100)
    private String promocode;
}
