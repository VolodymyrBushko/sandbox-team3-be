package com.exadel.discountwebapp.user_discount.mapper;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.repository.UserRepository;
import com.exadel.discountwebapp.user_discount.entity.UserDiscount;
import com.exadel.discountwebapp.user_discount.qrcode_generator.QRCodeGenerator;
import com.exadel.discountwebapp.user_discount.vo.UserDiscountRequestVO;
import com.exadel.discountwebapp.user_discount.vo.UserDiscountResponseVO;
import com.google.zxing.WriterException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserDiscountMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final QRCodeGenerator qrCodeGenerator;

    public UserDiscountMapper(UserRepository userRepository,
                              DiscountRepository discountRepository,
                              QRCodeGenerator qrCodeGenerator, DiscountMapper discountMapper) {
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    public UserDiscountResponseVO toVO(UserDiscount userDiscount) {
        UserDiscountResponseVO response = modelMapper.map(userDiscount, UserDiscountResponseVO.class);
        return response;
    }

    public UserDiscount toEntity(UserDiscountRequestVO request) throws IOException, WriterException {
        var userDiscount = modelMapper.map(request, UserDiscount.class);

        provideUserDiscountDependencies(request, userDiscount);
        return userDiscount;
    }


    private void provideUserDiscountDependencies(UserDiscountRequestVO request, UserDiscount userDiscount) throws IOException, WriterException {
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", request.getUserId()));
        var discount = discountRepository.findById(request.getDiscountId())
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", request.getDiscountId()));

        String qrcodeData = "First client name: " + user.getFirstName() +
                "\n Last client name: " + user.getLastName() +
                "\n Vendor: " + discount.getVendor().getTitle() +
                "\n Vendor email: " + discount.getVendor().getEmail() +
                "\n Discount: " + discount.getTitle() +
                "\n Discount start date: " + discount.getStartDate() +
                "\n Discount expiration date: " + discount.getExpirationDate();

        if (discount.getPromocode() != null) {
            qrcodeData += "\n Discount promocode " + discount.getPromocode();
        }

        userDiscount.setQrcode(qrCodeGenerator.getQRCodeImage(qrcodeData, 300, 300));

        userDiscount.setDiscount(discount);
        userDiscount.setUser(user);
    }
}