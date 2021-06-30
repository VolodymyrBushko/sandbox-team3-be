package com.exadel.discountwebapp.role.controller;

import com.exadel.discountwebapp.role.service.RoleService;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public RoleResponseVO findById(@NotNull @Positive @PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public List<RoleResponseVO> findAll() {
        return roleService.getAllRoles();
    }
}
