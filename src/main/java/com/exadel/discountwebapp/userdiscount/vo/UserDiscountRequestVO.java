package com.exadel.discountwebapp.userdiscount.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserDiscountRequestVO {

    @NotNull
    private Long discountId;

    @NotNull
    private Long userId;
}