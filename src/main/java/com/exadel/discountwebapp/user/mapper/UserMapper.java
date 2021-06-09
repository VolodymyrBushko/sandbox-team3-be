package com.exadel.discountwebapp.user.mapper;

import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.vo.UserRequestVO;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public User toEntity(UserRequestVO requestVO){
        return modelMapper.map(requestVO, User.class);
    }

    public UserResponseVO toVO(User entity) {
        return modelMapper.map(entity, UserResponseVO.class);
    }
}
