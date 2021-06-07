package com.exadel.discountwebapp.role.vo;

import com.exadel.discountwebapp.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseVO extends RoleBaseVO {
    private long id;

    public static RoleResponseVO fromRole(Role role) {
        return RoleResponseVO.builder()
                .id(role.getId())
                .name(role.getName())
                .users(role.getUsers())
                .created(role.getCreated())
                .modified(role.getModified())
                .build();
    }
}
