package com.exadel.discountwebapp.security;

import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signin")
    public TokenResponse signIn(
            @RequestParam(value = "login", required = true)
                    String login,
            @RequestParam(value = "password", required = true)
                    String password) {

        UserResponseVO userResponse = userService.findByLoginAndPassword(login, password);
        return new TokenResponse(jwtProvider.generateToken(userResponse.getEmail()));
    }
}
