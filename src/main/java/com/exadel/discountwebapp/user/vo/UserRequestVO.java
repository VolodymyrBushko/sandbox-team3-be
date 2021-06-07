package com.exadel.discountwebapp.user.vo;

import com.exadel.discountwebapp.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestVO extends UserBaseVO {

    public static User toUser(UserRequestVO request){
    return User.builder()
    .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(request.getPassword())
            .build();
    }
}
