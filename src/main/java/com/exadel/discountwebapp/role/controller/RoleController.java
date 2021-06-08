package com.exadel.discountwebapp.role.controller;
import com.exadel.discountwebapp.role.service.RoleService;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public RoleResponseVO findById(@PathVariable long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping()
    public List<RoleResponseVO> findAll() {
        return roleService.getAllRoles();
    }

}
