package com.exadel.discountwebapp.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRequestVO extends UserBaseVO {

    @NotNull
    private Long locationId;

    @NotNull
    private Long roleId;

    @NotBlank
    @Size(max = 510)
    private String password;
}
