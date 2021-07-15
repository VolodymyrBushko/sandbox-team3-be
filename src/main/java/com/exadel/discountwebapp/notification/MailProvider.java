package com.exadel.discountwebapp.notification;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailProvider {

    @Value("${notification.client.url}")
    private String clientUrl;

    @Value("${notification.discount.template}")
    private String discountEmailTemplate;

    private final VendorRepository vendorRepository;

    public Mail getMail(Discount discount) {
        Long vendorId = discount.getVendor().getId();
        List<String> subEmails = vendorRepository.findAllSubEmailsByVendorId(vendorId);

        if (subEmails.isEmpty()) {
            return null;
        }

        return Mail.builder()
                .subject(discount.getTitle())
                .template(discountEmailTemplate)
                .variables(getTemplateVariables(discount))
                .to(subEmails.toArray(new String[0]))
                .build();
    }

    private Map<String, Object> getTemplateVariables(Discount discount) {
        Map<String, Object> variables = new HashMap<>();
        String discountInfoUrl = String.format("%s/discounts/%d", clientUrl, discount.getId());
        String discountAmount = discount.getPercentage() != null
                ? String.format("-%.2f%%", discount.getPercentage())
                : String.format("-%.2fUSD", discount.getFlatAmount());

        variables.put("discountTitle", discount.getTitle());
        variables.put("imageUrl", discount.getImageUrl());
        variables.put("amount", discountAmount);
        variables.put("vendorTitle", discount.getVendor().getTitle());
        variables.put("categoryTitle", discount.getCategory().getTitle());
        variables.put("description", discount.getDescription());
        variables.put("infoUrl", discountInfoUrl);

        return variables;
    }
}
