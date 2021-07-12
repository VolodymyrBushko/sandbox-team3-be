package com.exadel.discountwebapp.notification;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMailSender mailSender;
    private final VendorRepository vendorRepository;

    public void sendNewDiscountNotification(Discount discount) {
        Long vendorId = discount.getVendor().getId();
        List<String> subEmails = vendorRepository.findAllSubEmailsByVendorId(vendorId);

        if (!subEmails.isEmpty()) {
            mailSender.sendMail(
                    discount.getTitle(),
                    discount.getShortDescription(),
                    subEmails.toArray(new String[0]));
        }
    }
}
