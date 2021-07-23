package com.exadel.discountwebapp.statistics.dto.extendeddto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ExtendedDiscountViews {
    String getTitle();
    String getShortDescription();
    String getDescription();
    String getPromocode();
    BigDecimal getPercentage();
    BigDecimal getFlatAmount();
    LocalDateTime getCreated();
    LocalDateTime getStartDate();
    LocalDateTime getExpirationDate();
    String getVendorTitle();
    String getCategoryTitle();
    Long getViewNumber();
    int getActivated();
}
