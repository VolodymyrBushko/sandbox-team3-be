package com.exadel.discountwebapp.integrationtest.service;

import com.exadel.discountwebapp.DiscountWebApplication;
import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.mapper.RoleMapper;
import com.exadel.discountwebapp.role.repository.RoleRepository;
import com.exadel.discountwebapp.role.service.RoleService;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(classes = DiscountWebApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleServiceImplIntegrationTest {

    private final RoleMapper roleMapper;

    private RoleRepository roleRepository;

    private RoleService roleService;

    private Role roleUser;
    private Role roleAdmin;

    @Autowired
    public RoleServiceImplIntegrationTest(RoleMapper roleMapper, RoleRepository roleRepository, RoleService roleService) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @BeforeEach
    void setUp() {
        roleUser = Role.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .name("USER")
                .build();
        roleUser = roleRepository.save(roleUser);

        roleAdmin = Role.builder()
                .id(2L)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .name("ADMIN")
                .build();
        roleAdmin = roleRepository.save(roleAdmin);
    }

    @Rollback
    @Test
    void shouldGetAllRoles() {
        List<RoleResponseVO> expected = List.of(roleMapper.toVO(roleUser), roleMapper.toVO(roleAdmin));
        List<RoleResponseVO> actual = roleService.getAllRoles();
        assertEquals(expected, actual);
    }

    @Rollback
    @Test
    void shouldGetRoleById() {
        var expected = roleMapper.toVO(roleAdmin);
        var id = roleAdmin.getId();
        var actual = roleService.getRoleById(2);
        assertEquals(expected, actual);
    }
}
