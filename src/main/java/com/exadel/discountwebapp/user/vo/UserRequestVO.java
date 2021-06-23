package com.exadel.discountwebapp.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequestVO extends UserBaseVO {

    @NotNull
    private Long locationId;

    @NotNull
    private Long roleId;
}
