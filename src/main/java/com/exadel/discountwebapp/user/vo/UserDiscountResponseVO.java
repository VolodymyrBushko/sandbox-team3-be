package com.exadel.discountwebapp.user.vo;

import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDiscountResponseVO extends UserDiscountBaseVO{

    @NotNull
    private UserResponseVO user;

    @NotNull
    private DiscountResponseVO discount;
}
