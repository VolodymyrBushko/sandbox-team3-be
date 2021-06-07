package com.exadel.discountwebapp.role.service;

import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.exception.RoleNotFoundException;
import com.exadel.discountwebapp.role.repository.RoleRepository;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleResponseVO getRoleById(long id) {
        Optional<Role> role = roleRepository.findById(id);
        return RoleResponseVO.fromRole(role.orElseThrow(() -> new RoleNotFoundException(String.format("No role exist with given id = %d", id))));
    }

    public List<RoleResponseVO> getAllRoles() {
        List<RoleResponseVO> listRoles = new ArrayList<>();
        roleRepository.findAll().forEach(r -> listRoles.add(RoleResponseVO.fromRole(r)));
        return listRoles;
    }
}
