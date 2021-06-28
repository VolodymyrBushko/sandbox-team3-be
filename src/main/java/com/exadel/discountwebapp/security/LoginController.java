package com.exadel.discountwebapp.security;

import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SigninVO signinVO) {
        UserResponseVO userResponse = null;
        try {
            userResponse = userService.findByLoginAndPassword(signinVO);
        } catch (EntityNotFoundException e) {
            // throw new EntityNotFoundException("Login or Password is wrong! Try again!");
        }
        return new TokenResponse(jwtProvider.generateToken(userResponse.getEmail()));
    }
}
