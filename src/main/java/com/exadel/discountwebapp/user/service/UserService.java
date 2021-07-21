package com.exadel.discountwebapp.user.service;

import com.exadel.discountwebapp.baseclasses.BaseEntityMapper;
import com.exadel.discountwebapp.baseclasses.BaseFilterService;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.role.mapper.RoleMapper;
import com.exadel.discountwebapp.security.SigninVO;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.mapper.UserMapper;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends BaseFilterService<User, UserResponseVO> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserResponseVO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
        return userMapper.toVO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserResponseVO findByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", email));
        return userMapper.toVO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "email", email));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponseVO findByLoginAndPassword(SigninVO signinVO) {
        String email = signinVO.getEmail();
        String password = signinVO.getPassword();
        User user = userRepository.findUserByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Can not find User by Login or Password"));

        return new UserResponseVO().builder()
                .email(email)
                .role(roleMapper.toVO(user.getRole()))
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Long> findSubscribersIdsByEmail(String email) {
        return userRepository.findSubscribersIdsByEmail(email);
    }

    @Override
    protected JpaSpecificationExecutor<User> getEntityRepository() {
        return userRepository;
    }

    @Override
    protected BaseEntityMapper<User, UserResponseVO> getEntityToVOMapper() {
        return userMapper;
    }
}
