package com.exadel.discountwebapp.user.vo;

import com.exadel.discountwebapp.user.entity.User;
import lombok.Data;
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

    public static UserResponseVO fromUser(User user) {
        return UserResponseVO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
