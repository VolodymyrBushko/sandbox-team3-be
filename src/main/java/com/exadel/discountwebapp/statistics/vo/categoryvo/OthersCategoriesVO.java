package com.exadel.discountwebapp.statistics.vo.categoryvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OthersCategoriesVO extends CategoryVO {
    private String othersTitle;
    private Long othersQuantity;
}
