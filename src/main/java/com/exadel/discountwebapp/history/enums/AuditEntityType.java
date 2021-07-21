package com.exadel.discountwebapp.history.enums;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.vendor.entity.Vendor;

public enum AuditEntityType {
    CATEGORY(Category.class),
    DISCOUNT(Discount.class),
    VENDOR(Vendor.class);

    public final Class entityClass;

    AuditEntityType(Class entityClass) {
        this.entityClass = entityClass;
    }
}