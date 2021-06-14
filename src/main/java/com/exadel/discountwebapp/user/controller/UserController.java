package com.exadel.discountwebapp.user.controller;

import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseVO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseVO findById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
