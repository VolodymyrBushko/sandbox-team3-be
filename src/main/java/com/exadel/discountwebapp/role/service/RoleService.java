package com.exadel.discountwebapp.role.service;

import com.exadel.discountwebapp.role.vo.RoleResponseVO;

import java.util.List;

public interface RoleService {
    RoleResponseVO getRoleById(long id);

    List<RoleResponseVO> getAllRoles();
}
