package com.exadel.discountwebapp.user;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.role.entity.Role;
import com.exadel.discountwebapp.role.repository.RoleRepository;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    LocationRepository locationRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        Role role1 = Role.builder()
                .id(1L)
                .name("USER")
                .build();

        Role role2 = Role.builder()
                .id(2L)
                .name("ADMIN")
                .build();

        Location location = Location.builder()
                .id(1L)
                .city("Kiev")
                .country("Ukraine")
                .build();

        user1 = User.builder()
                .id(1L)
                .firstName("User1firstName")
                .lastName("User1lastName")
                .password("password123")
                .email("email@ukr.net")
                .role(role1)
                .location(location)
                .build();

        user2 = User.builder()
                .id(2L)
                .firstName("User2firstName")
                .lastName("User2lastName")
                .password("password12345")
                .email("email123@ukr.net")
                .role(role2)
                .location(location)
                .build();

        roleRepository.saveAll(List.of(role1, role2));
        locationRepository.save(location);
        userRepository.saveAll(List.of(user1, user2));
    }


    @Test
    void shouldGetAllUsers() {
        List<UserResponseVO> users = userService.findAll();

        Assertions.assertEquals(user1.getId(), users.get(0).getId());
        Assertions.assertEquals(user1.getFirstName(), users.get(0).getFirstName());
        Assertions.assertEquals(user1.getLastName(), users.get(0).getLastName());
        Assertions.assertEquals(user1.getPassword(), users.get(0).getPassword());
        Assertions.assertEquals(user1.getEmail(), users.get(0).getEmail());

        Assertions.assertEquals(user2.getId(), users.get(1).getId());
        Assertions.assertEquals(user2.getFirstName(), users.get(1).getFirstName());
        Assertions.assertEquals(user2.getLastName(), users.get(1).getLastName());
        Assertions.assertEquals(user2.getPassword(), users.get(1).getPassword());
        Assertions.assertEquals(user2.getEmail(), users.get(1).getEmail());

        Assertions.assertEquals(2, users.size());
    }

    @Test
    void shouldGetUserById() {
        var id = user1.getId();
        var actual = userService.findById(id);
        Assertions.assertEquals(user1.getId(), actual.getId());
        Assertions.assertEquals(user1.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(user1.getLastName(), actual.getLastName());
        Assertions.assertEquals(user1.getPassword(), actual.getPassword());
        Assertions.assertEquals(user1.getEmail(), actual.getEmail());
    }

    @Test
    void shouldThrowExceptionIfNoUserFoundById(){
        List<UserResponseVO> users = userService.findAll();

        Assertions.assertNotEquals(3L, users.size());

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(3L));
    }
}
