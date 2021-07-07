package com.exadel.discountwebapp.userdiscount.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDiscountRequestVO extends UserDiscountBaseVO{

    @NotNull
    private Long discountId;

    @NotNull
    private Long userId;
}