package com.exadel.discountwebapp.role;

import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.repository.RoleRepository;
import com.exadel.discountwebapp.role.service.RoleService;
import com.exadel.discountwebapp.role.vo.RoleResponseVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
@SpringBootTest
public class RoleServiceIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private Role roleUser;
    private Role roleAdmin;

    @BeforeEach
    void setUp() {
        roleUser = Role.builder()
                .id(1L)
                .name("USER")
                .build();

        roleAdmin = Role.builder()
                .id(2L)
                .name("ADMIN")
                .build();

        transactionTemplate.execute(status -> {
            return roleRepository.saveAll(List.of(roleUser, roleAdmin));
        });
    }

    @Test
    void shouldGetAllRoles() {
        List<RoleResponseVO> actual = roleService.getAllRoles();
        assertEquals(roleUser.getId(), actual.get(0).getId());
        assertEquals(roleUser.getName(), actual.get(0).getName());
        assertEquals(roleAdmin.getId(), actual.get(1).getId());
        assertEquals(roleAdmin.getName(), actual.get(1).getName());
        assertEquals(2, actual.size());
    }

    @Test
    void shouldGetRoleById() {
        var id = roleAdmin.getId();
        var actual = roleService.getRoleById(id);
        assertEquals(roleAdmin.getId(), actual.getId());
        assertEquals(roleAdmin.getName(), actual.getName());
    }
}
