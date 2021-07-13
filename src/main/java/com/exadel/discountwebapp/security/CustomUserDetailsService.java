package com.exadel.discountwebapp.security;

import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResponseVO user = userService.findByEmail(email);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
