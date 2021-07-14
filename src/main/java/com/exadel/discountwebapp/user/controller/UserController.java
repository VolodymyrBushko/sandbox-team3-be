package com.exadel.discountwebapp.user.controller;

import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserResponseVO> findAll(@RequestParam(value = "query", defaultValue = "", required = false) String query,
                                        @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.findAll(query, pageable);
    }

    @GetMapping("/{id}")
    public UserResponseVO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/current")
    public UserResponseVO getCurrentUser(Principal principal) {
        return userService.findByEmail(principal.getName());
    }

    @GetMapping("/subscribersIds")
    public List<Long> findSubscribersIds(Principal principal) {
        return userService.findSubscribersIdsByEmail(principal.getName());
    }
}
