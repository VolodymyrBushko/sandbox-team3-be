package com.exadel.discountwebapp.user.mapper;

import com.exadel.discountwebapp.common.BaseEntityMapper;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import com.exadel.discountwebapp.role.mapper.RoleMapper;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@Component
public class UserMapper implements BaseEntityMapper<User, UserResponseVO> {

    private final ModelMapper modelMapper = new ModelMapper();
    private final LocationMapper locationMapper;
    private final RoleMapper roleMapper;

    public UserMapper(LocationMapper locationMapper, RoleMapper roleMapper) {
        this.locationMapper = locationMapper;
        this.roleMapper = roleMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public UserResponseVO toVO(User entity) {
        UserResponseVO response = modelMapper.map(entity, UserResponseVO.class);
        LocationResponseVO location = locationMapper.toVO(entity.getLocation());
        RoleResponseVO role = roleMapper.toVO(entity.getRole());
        response.setLocation(location);
        response.setRole(role);
        return response;
    }
}