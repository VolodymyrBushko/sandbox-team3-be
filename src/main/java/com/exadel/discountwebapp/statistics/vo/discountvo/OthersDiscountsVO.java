package com.exadel.discountwebapp.statistics.vo.discountvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OthersDiscountsVO extends DiscountVO {
    private String othersTitle;
    private Long othersQuantity;
}
