package com.exadel.discountwebapp.user.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserResponseVO extends UserBaseVO {

    private long id;
}
