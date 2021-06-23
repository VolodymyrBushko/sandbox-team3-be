package com.exadel.discountwebapp.user.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/user-init.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clean-up.sql")
class UserServiceIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void shouldGetAllUsers() {

        var expectedItr = userRepository.findAll();
        var expected = Lists.newArrayList(expectedItr);
        var actual = userService.findAll();

        Assertions.assertEquals(2, actual.size());

        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.get(0).getFirstName(), actual.get(0).getFirstName());
        Assertions.assertEquals(expected.get(0).getLastName(), actual.get(0).getLastName());
        Assertions.assertEquals(expected.get(0).getPassword(), actual.get(0).getPassword());
        Assertions.assertEquals(expected.get(0).getEmail(), actual.get(0).getEmail());
        Assertions.assertEquals(expected.get(0).getLocation().getId(), actual.get(0).getLocation().getId());
        Assertions.assertEquals(expected.get(0).getRole().getId(), actual.get(0).getRole().getId());

        Assertions.assertEquals(expected.get(1).getId(), actual.get(1).getId());
        Assertions.assertEquals(expected.get(1).getFirstName(), actual.get(1).getFirstName());
        Assertions.assertEquals(expected.get(1).getLastName(), actual.get(1).getLastName());
        Assertions.assertEquals(expected.get(1).getPassword(), actual.get(1).getPassword());
        Assertions.assertEquals(expected.get(1).getEmail(), actual.get(1).getEmail());
        Assertions.assertEquals(expected.get(1).getLocation().getId(), actual.get(1).getLocation().getId());
        Assertions.assertEquals(expected.get(1).getRole().getId(), actual.get(1).getRole().getId());
    }

    @Test
    void shouldGetUserById() {
        var id = 1L;
        var expected = userRepository.findById(id).get();
        var actual = userService.findById(id);

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getLastName(), actual.getLastName());
        Assertions.assertEquals(expected.getPassword(), actual.getPassword());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        assertNotNull(actual.getLocation());
        assertNotNull(actual.getRole());
        Assertions.assertEquals(expected.getLocation().getId(), actual.getLocation().getId());
        Assertions.assertEquals(expected.getRole().getId(), actual.getRole().getId());
    }

    @Test
    void shouldThrowExceptionIfNoUserFoundById() {
        List<UserResponseVO> users = userService.findAll();

        Assertions.assertNotEquals(3L, users.size());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(3L));
    }
}