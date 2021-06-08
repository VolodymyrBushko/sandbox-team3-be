package com.exadel.discountwebapp.role.mapper;

import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.vo.RoleRequestVO;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponseVO toVO(Role entity) {
        return RoleResponseVO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public Role toEntity(RoleRequestVO requestVO) {
        return Role.builder()
                .name(requestVO.getName())
                .build();
    }
}
