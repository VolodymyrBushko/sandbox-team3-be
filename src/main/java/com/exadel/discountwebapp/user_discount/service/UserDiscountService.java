package com.exadel.discountwebapp.user_discount.service;

import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user_discount.entity.UserDiscount;
import com.exadel.discountwebapp.user_discount.mapper.UserDiscountMapper;
import com.exadel.discountwebapp.user_discount.repository.UserDiscountRepository;
import com.exadel.discountwebapp.user_discount.vo.UserDiscountRequestVO;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserDiscountService {

    private final UserDiscountRepository userDiscountRepository;
    private final UserDiscountMapper userDiscountMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public byte[] create(UserDiscountRequestVO request) throws IOException, WriterException {
        UserDiscount userDiscount = userDiscountMapper.toEntity(request);
        if (userDiscountRepository.existsById(new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId()))) {
            throw new EntityAlreadyExistsException(UserDiscount.class, "id", userDiscount.getId());
        }
        userDiscountRepository.save(userDiscount);
        return userDiscount.getQrcode();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public byte[] findQRCodeByUserDiscountId(UserDiscountRequestVO request) {
        UserDiscount.UserDiscountId userDiscountId = new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId());
        UserDiscount discount = userDiscountRepository.findById(userDiscountId)
                .orElseThrow(() -> new EntityNotFoundException(UserDiscount.class, "id", userDiscountId));
        return discount.getQrcode();
    }
}