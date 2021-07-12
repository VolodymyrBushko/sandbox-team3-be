package com.exadel.discountwebapp.userdiscount.mapper;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.userdiscount.entity.UserDiscount;
import com.exadel.discountwebapp.userdiscount.vo.UserDiscountRequestVO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDiscountMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    public UserDiscountMapper(UserRepository userRepository,
                              DiscountRepository discountRepository) {
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
    }

    public UserDiscount toEntity(UserDiscountRequestVO request) {
        var userDiscount = modelMapper.map(request, UserDiscount.class);
        provideUserDiscountDependencies(request, userDiscount);
        return userDiscount;
    }

    private void provideUserDiscountDependencies(UserDiscountRequestVO request, UserDiscount userDiscount) {
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", request.getUserId()));
        var discount = discountRepository.findById(request.getDiscountId())
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", request.getDiscountId()));
        userDiscount.setDiscount(discount);
        userDiscount.setUser(user);
    }
}