package com.exadel.discountwebapp.userdiscount.service;

import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.userdiscount.entity.UserDiscount;
import com.exadel.discountwebapp.userdiscount.mapper.UserDiscountMapper;
import com.exadel.discountwebapp.userdiscount.qrcodegenerator.QRCodeGenerator;
import com.exadel.discountwebapp.userdiscount.repository.UserDiscountRepository;
import com.exadel.discountwebapp.userdiscount.vo.UserDiscountRequestVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDiscountService {

    private final Integer QRCODE_WIDTH = 300;
    private final Integer QRCODE_HEIGHT = 300;

    private final UserDiscountRepository userDiscountRepository;
    private final UserDiscountMapper userDiscountMapper;
    private final QRCodeGenerator qrCodeGenerator;

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRED)
    public byte[] create(UserDiscountRequestVO request) {
        UserDiscount.UserDiscountId userDiscountId = new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId());
        if (userDiscountRepository.existsById(userDiscountId)) {
            throw new EntityAlreadyExistsException(UserDiscount.class, "id", userDiscountId);
        }
        UserDiscount userDiscount = userDiscountMapper.toEntity(request);
        byte[] qrcode = qrCodeGenerator.generateQRCodeImage(dataForQRCode(request), QRCODE_WIDTH, QRCODE_HEIGHT);
        userDiscountRepository.save(userDiscount);
        return qrcode;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public byte[] getQRCodeByUserDiscountId(UserDiscountRequestVO request) {
        UserDiscount.UserDiscountId userDiscountId = new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId());
        UserDiscount discount = userDiscountRepository.findById(userDiscountId)
                .orElseThrow(() -> new EntityNotFoundException(UserDiscount.class, "id", userDiscountId));
        byte[] qrcode = qrCodeGenerator.generateQRCodeImage(dataForQRCode(request), QRCODE_WIDTH, QRCODE_HEIGHT);
        return qrcode;
    }

    @SneakyThrows
    private String dataForQRCode(UserDiscountRequestVO request) {
        //TODO: add valid url adress
        //String data = "http://localhost:8080/api/qrcode/" + request.getUserId() + "/" + request.getDiscountId();

        String data = "https://sandbox-discount.herokuapp.com/" + request.getUserId() + "/" + request.getDiscountId();
        return data;
    }
}