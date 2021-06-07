package com.exadel.discountwebapp.role.vo;

import com.exadel.discountwebapp.role.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleRequestVO extends RoleBaseVO {

    public static Role toRole(RoleRequestVO request) {
        return Role.builder()
                .name(request.getName())
                .build();
    }
}
