package com.exadel.discountwebapp.role.mapper;

import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.vo.RoleRequestVO;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public RoleMapper() {
        configureModelMapper(modelMapper);
    }

    public RoleResponseVO toVO(Role entity) {
        return modelMapper.map(entity, RoleResponseVO.class);
    }

    public Role toEntity(RoleRequestVO requestVO) {
        return modelMapper.map(requestVO, Role.class);
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
