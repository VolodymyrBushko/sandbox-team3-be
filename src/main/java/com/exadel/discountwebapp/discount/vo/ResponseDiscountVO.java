package com.exadel.discountwebapp.discount.vo;

import com.exadel.discountwebapp.discount.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDiscountVO extends BaseDiscountVO {

    private long id;

    public static ResponseDiscountVO fromDiscount(Discount discount) {
        return ResponseDiscountVO.builder()
                .id(discount.getId())
                .title(discount.getTitle())
                .shortDescription(discount.getShortDescription())
                .description(discount.getDescription())
                .imageUrl(discount.getImageUrl())
                .flatAmount(discount.getFlatAmount())
                .price(discount.getPrice())
                .startDate(discount.getStartDate())
                .expirationDate(discount.getExpirationDate())
                .percentage(discount.getPercentage())
                .quantity(discount.getQuantity())
                .perUser(discount.getPerUser())
                .build();
    }
}
