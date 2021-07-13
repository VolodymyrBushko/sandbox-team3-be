package com.exadel.discountwebapp.history.entityclassenum;

import com.exadel.discountwebapp.exception.exception.client.NotAllowedOperationException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum HistoryEnum {
    category("com.exadel.discountwebapp.category.entity.Category"),
    discount("com.exadel.discountwebapp.discount.entity.Discount"),
    vendor("com.exadel.discountwebapp.vendor.entity.Vendor");

    public final String getClazz;

    HistoryEnum(String getClazz) {
        this.getClazz = getClazz;
    }

    public String getHistoryOfClass(String input) {
        return String.valueOf(Arrays.stream(values())
                .filter(e -> e.getClazz.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new NotAllowedOperationException(input)));
    }
}