package com.exadel.discountwebapp.role.service;

import com.exadel.discountwebapp.role.repository.RoleRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/role-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class RoleServiceIntegrationTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldGetAllRoles() {
        var expectedItr = roleRepository.findAll();
        var expected = Lists.newArrayList(expectedItr);
        var actual = roleService.getAllRoles();

        assertEquals(2, actual.size());

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
    }

    @Test
    void shouldGetRoleById() {
        var id = 1L;
        var expected = roleRepository.findById(id).get();
        var actual = roleService.getRoleById(id);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }
}
