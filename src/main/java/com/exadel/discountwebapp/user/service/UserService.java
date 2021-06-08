package com.exadel.discountwebapp.user.service;

import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.exception.UserNotFoundException;
import com.exadel.discountwebapp.user.mapper.UserMapper;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseVO findById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with id = %d\", id)"));
        return UserMapper.toVO(user);
    }

    public List<UserResponseVO> findAll() {
        List<UserResponseVO> response = new ArrayList<>();
        userRepository.findAll().forEach(user -> response.add(UserMapper.toVO(user)));
        return response;
    }
}