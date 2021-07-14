package com.exadel.discountwebapp.notification;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMailSender mailSender;

    public void sendNewDiscountNotification(Discount discount, List<String> subEmails) {
        String subject = discount.getTitle();
        String from = "exadelteam3@gmail.com";
        String[] to = subEmails.toArray(new String[0]);
        String template = "new-discount-email";

        Map<String, Object> variables = new HashMap<>();

        variables.put("discountTitle", discount.getTitle());
        variables.put("discountImageUrl", discount.getImageUrl());
        variables.put("discountDiscount", discount.getPercentage() != null ? discount.getPercentage() : discount.getFlatAmount());
        variables.put("vendorTitle", discount.getVendor().getTitle());
        variables.put("discountCategory", discount.getCategory().getTitle());
        variables.put("discountDescription", discount.getDescription());
        variables.put("discountInfoUrl", "#");

        Mail mail = Mail.builder()
                .subject(subject)
                .from(from)
                .to(to)
                .template(template)
                .variables(variables)
                .build();

        mailSender.sendMail(mail);
    }
}
