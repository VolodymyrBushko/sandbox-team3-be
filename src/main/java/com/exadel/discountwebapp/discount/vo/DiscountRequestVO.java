package com.exadel.discountwebapp.discount.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DiscountRequestVO extends DiscountBaseVO {

    @NotNull
    @Positive
    private Long categoryId;

    @NotNull
    @Positive
    private Long vendorId;
}
