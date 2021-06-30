package com.exadel.discountwebapp.user.vo;

import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
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
public class UserResponseVO extends UserBaseVO {

    @NotNull
    private Long id;

    @NotNull
    private LocationResponseVO location;

    @NotNull
    private RoleResponseVO role;
}
