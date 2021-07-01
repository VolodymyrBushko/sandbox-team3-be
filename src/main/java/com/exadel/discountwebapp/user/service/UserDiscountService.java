package com.exadel.discountwebapp.user.service;

import com.exadel.discountwebapp.user.entity.UserDiscount;
import com.exadel.discountwebapp.user.mapper.UserDiscountMapper;
import com.exadel.discountwebapp.user.repository.UserDiscountRepository;
import com.exadel.discountwebapp.user.vo.UserDiscountRequestVO;
import com.exadel.discountwebapp.user.vo.UserDiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDiscountService {

    private final UserDiscountRepository userDiscountRepository;
    private final UserDiscountMapper userDiscountMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> create(UserDiscountRequestVO request) {
        UserDiscount userDiscount = userDiscountMapper.toEntity(request);
        userDiscountRepository.save(userDiscount);
//        return userDiscountMapper.toVO(userDiscount);
        ResponseEntity<String> responseEntity =
                new ResponseEntity<String>("Discount " +request.getDiscountId() + " added to user " + request.getDiscountId(),
                        HttpStatus.OK);
        return responseEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UserDiscountResponseVO> findAllUsersByDiscountId(Long discountId) {
        List<UserDiscountResponseVO> response = new ArrayList<>();
        userDiscountRepository.findAllByDiscount_Id(discountId).forEach(userDiscount -> response.add(userDiscountMapper.toVO(userDiscount)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UserDiscountResponseVO> findAllDiscountsByUserId(Long userId) {
        List<UserDiscountResponseVO> response = new ArrayList<>();
        userDiscountRepository.findAllByUser_Id(userId).forEach(userDiscount -> response.add(userDiscountMapper.toVO(userDiscount)));
        return response;
    }
}