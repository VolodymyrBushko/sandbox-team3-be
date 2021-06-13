package com.exadel.discountwebapp.role.controller;

import com.exadel.discountwebapp.role.service.RoleService;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{id}")
    public RoleResponseVO findById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping()
    public List<RoleResponseVO> findAll() {
        return roleService.getAllRoles();
    }

}
