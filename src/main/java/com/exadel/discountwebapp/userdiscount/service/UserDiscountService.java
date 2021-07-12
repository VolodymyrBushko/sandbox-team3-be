package com.exadel.discountwebapp.userdiscount.service;

import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.userdiscount.entity.UserDiscount;
import com.exadel.discountwebapp.userdiscount.mapper.UserDiscountMapper;
import com.exadel.discountwebapp.userdiscount.qrcodegenerator.QRCodeGenerator;
import com.exadel.discountwebapp.userdiscount.repository.UserDiscountRepository;
import com.exadel.discountwebapp.userdiscount.vo.UserDiscountRequestVO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDiscountService {

    private final String APP_URL;

    private final Integer QRCODE_WIDTH = 300;
    private final Integer QRCODE_HEIGHT = 300;

    private final UserDiscountRepository userDiscountRepository;
    private final UserDiscountMapper userDiscountMapper;
    private final QRCodeGenerator qrCodeGenerator;

    @Autowired
    public UserDiscountService(@Value("${app.url}") String APP_URL,
                               UserDiscountRepository userDiscountRepository,
                               UserDiscountMapper userDiscountMapper,
                               QRCodeGenerator qrCodeGenerator) {
        this.APP_URL = APP_URL;
        this.userDiscountRepository = userDiscountRepository;
        this.userDiscountMapper = userDiscountMapper;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> addDiscount(UserDiscountRequestVO request) {
        UserDiscount.UserDiscountId userDiscountId = new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId());
        if (userDiscountRepository.existsById(userDiscountId)) {
            throw new EntityAlreadyExistsException(UserDiscount.class, "id", userDiscountId);
        }
        UserDiscount userDiscount = userDiscountMapper.toEntity(request);
        userDiscountRepository.save(userDiscount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public byte[] getQRCodeByUserDiscountId(UserDiscountRequestVO request) {
        if (!(userDiscountRepository.existsById(new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId())))) {
            throw new EntityNotFoundException(UserDiscount.class, "id", new UserDiscount.UserDiscountId(request.getUserId(), request.getDiscountId()));
        }
        return qrCodeGenerator.generateQRCodeImage(dataForQRCode(request), QRCODE_WIDTH, QRCODE_HEIGHT);
    }

    @SneakyThrows
    private String dataForQRCode(UserDiscountRequestVO request) {
        return APP_URL + "/" + request.getUserId() + "/" + request.getDiscountId();
    }
}