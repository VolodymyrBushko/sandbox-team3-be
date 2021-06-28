package com.exadel.discountwebapp.user.service;

import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.role.mapper.RoleMapper;
import com.exadel.discountwebapp.security.CustomUserDetails;
import com.exadel.discountwebapp.security.SigninVO;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.mapper.UserMapper;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final UserMapper mapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserResponseVO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", "id", id));
        return mapper.toVO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UserResponseVO> findAll() {
        List<UserResponseVO> response = new ArrayList<>();
        userRepository.findAll().forEach(user -> response.add(mapper.toVO(user)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserResponseVO findByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User", "email", email));
        return mapper.toVO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponseVO findByLoginAndPassword(SigninVO signinVO) {
        String email = signinVO.getEmail();
        String password = signinVO.getPassword();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Wrong email or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong email or password");
        }
        return new UserResponseVO().builder()
                .email(email)
                .password(password)
                .role(roleMapper.toVO(user.getRole()))
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String getExpirationLocalDate() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime localDate = customUserDetails.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' hh:mm");
        return localDate.format(formatter);
    }
}