package com.exadel.discountwebapp.role.service;

import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.exception.RoleNotFoundException;
import com.exadel.discountwebapp.role.mapper.RoleMapper;
import com.exadel.discountwebapp.role.repository.RoleRepository;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public RoleResponseVO getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return roleMapper.toVO(role.orElseThrow(() -> new RoleNotFoundException(String.format("No role exist with given id = %d", id))));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<RoleResponseVO> getAllRoles() {
        List<RoleResponseVO> roles = new ArrayList<>();
        roleRepository.findAll().forEach(r -> roles.add(roleMapper.toVO(r)));
        return roles;
    }
}
