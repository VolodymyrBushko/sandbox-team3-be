package com.exadel.discountwebapp.role;

import com.exadel.discountwebapp.DiscountWebApplication;
import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.mapper.RoleMapper;
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

@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts =
        "classpath:cleanup.sql")
@SpringBootTest(classes = DiscountWebApplication.class)
public class RoleServiceIntegrationTest {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TransactionTemplate transactiontemplate;

    private Role roleUser;
    private Role roleAdmin;

    @BeforeEach
    void setUp() {
        transactiontemplate.execute(status -> {
            roleUser = Role.builder()
                    .id(1L)
                    .name("USER")
                    .build();

            roleAdmin = Role.builder()
                    .id(2L)
                    .name("ADMIN")
                    .build();

            roleRepository.saveAll(List.of(roleUser, roleAdmin));
            return "";
        });
    }

    @Test
    void shouldGetAllRoles() {
        List<RoleResponseVO> expected = List.of(roleMapper.toVO(roleUser), roleMapper.toVO(roleAdmin));
        List<RoleResponseVO> actual = roleService.getAllRoles();
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetRoleById() {
        var expected = roleMapper.toVO(roleAdmin);
        var id = roleAdmin.getId();
        var actual = roleService.getRoleById(id);
        assertEquals(expected, actual);
    }
}
