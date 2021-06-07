package com.exadel.discountwebapp.user.controller;

import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseVO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseVO findById(@PathVariable long id) {
        return userService.findById(id);
    }
}
