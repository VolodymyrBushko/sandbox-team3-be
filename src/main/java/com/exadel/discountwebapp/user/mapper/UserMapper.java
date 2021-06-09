package com.exadel.discountwebapp.user.mapper;

import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.vo.UserRequestVO;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void init(){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public User toEntity(UserRequestVO requestVO){
        return modelMapper.map(requestVO, User.class);
    }

    public UserResponseVO toVO(User entity) {
        return modelMapper.map(entity, UserResponseVO.class);
    }
}
