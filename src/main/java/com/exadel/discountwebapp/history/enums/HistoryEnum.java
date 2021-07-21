package com.exadel.discountwebapp.history.enums;

public enum HistoryEnum {
    CATEGORY("com.exadel.discountwebapp.category.entity.Category"),
    DISCOUNT("com.exadel.discountwebapp.discount.entity.Discount"),
    VENDOR("com.exadel.discountwebapp.vendor.entity.Vendor");

    public final String getClazz;

    HistoryEnum(String getClazz) {
        this.getClazz = getClazz;
    }
}