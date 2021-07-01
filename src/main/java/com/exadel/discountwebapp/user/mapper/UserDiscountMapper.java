package com.exadel.discountwebapp.user.mapper;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.entity.UserDiscount;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user.vo.UserDiscountRequestVO;
import com.exadel.discountwebapp.user.vo.UserDiscountResponseVO;
import com.exadel.discountwebapp.user.vo.UserResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDiscountMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;

    public UserDiscountMapper(UserMapper userMapper, UserRepository userRepository, DiscountMapper discountMapper, DiscountRepository discountRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.discountMapper = discountMapper;
        this.discountRepository = discountRepository;
    }

    public UserDiscountResponseVO toVO(UserDiscount userDiscount) {
        UserDiscountResponseVO response = modelMapper.map(userDiscount, UserDiscountResponseVO.class);
        UserResponseVO user = userMapper.toVO(userDiscount.getUser());
        DiscountResponseVO discount = discountMapper.toVO(userDiscount.getDiscount());
        response.setDiscount(discount);
        response.setUser(user);
        return response;
    }

    public UserDiscount toEntity(UserDiscountRequestVO request) {
        var userDiscount = modelMapper.map(request, UserDiscount.class);
        provideDiscountDependencies(request, userDiscount);
        return userDiscount;
    }


    private void provideDiscountDependencies(UserDiscountRequestVO request, UserDiscount userDiscount) {
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", request.getUserId()));
        var discount = discountRepository.findById(request.getDiscountId())
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", request.getDiscountId()));

        userDiscount.setDiscount(discount);
        userDiscount.setUser(user);
    }
}
