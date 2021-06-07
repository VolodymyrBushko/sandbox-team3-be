package com.exadel.discountwebapp.user.mapper;

import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.vo.UserRequestVO;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserRequestVO requestVO){
        return User.builder()
                .firstName(requestVO.getFirstName())
                .lastName(requestVO.getLastName())
                .email(requestVO.getEmail())
                .password(requestVO.getPassword())
                .build();
    }

    public static UserResponseVO toVO(User entity) {
        return UserResponseVO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
