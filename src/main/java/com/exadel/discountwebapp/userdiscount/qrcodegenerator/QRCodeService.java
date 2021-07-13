package com.exadel.discountwebapp.userdiscount.qrcodegenerator;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QRCodeService {

    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    public QRCodeResponseVO qrCodeViewPage(Long userId, Long discountId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId));
        var discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", discountId));

        QRCodeResponseVO qrcodeResponse = new QRCodeResponseVO();
        qrcodeResponse.setFirstName(user.getFirstName());
        qrcodeResponse.setLastName(user.getLastName());
        qrcodeResponse.setImageUrl(user.getImageUrl());

        qrcodeResponse.setVendorTitle(discount.getVendor().getTitle());
        qrcodeResponse.setVendorEmail(discount.getVendor().getEmail());

        qrcodeResponse.setDiscountTitle(discount.getTitle());
        qrcodeResponse.setPromocode(discount.getPromocode());
        qrcodeResponse.setDiscountStartDate(discount.getStartDate());
        qrcodeResponse.setDiscountExpirationDate(discount.getExpirationDate());

        return qrcodeResponse;
    }
}
