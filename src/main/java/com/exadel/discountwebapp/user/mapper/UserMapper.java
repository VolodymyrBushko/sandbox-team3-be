package com.exadel.discountwebapp.user.mapper;

import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public UserMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public UserResponseVO toVO(User entity) {
        return modelMapper.map(entity, UserResponseVO.class);
    }
}