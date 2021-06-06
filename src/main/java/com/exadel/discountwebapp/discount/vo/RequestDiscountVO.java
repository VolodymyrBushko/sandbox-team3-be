package com.exadel.discountwebapp.discount.vo;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDiscountVO extends BaseDiscountVO {

    public static Discount toDiscount(RequestDiscountVO request) {
        return Discount.builder()
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .flatAmount(request.getFlatAmount())
                .price(request.getPrice())
                .startDate(request.getStartDate())
                .expirationDate(request.getExpirationDate())
                .percentage(request.getPercentage())
                .quantity(request.getQuantity())
                .perUser(request.getPerUser())
                .build();
    }
}
