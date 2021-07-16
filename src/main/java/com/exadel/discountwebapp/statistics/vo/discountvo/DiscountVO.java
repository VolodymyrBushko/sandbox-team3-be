package com.exadel.discountwebapp.statistics.vo.discountvo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountVO  {
    private Long id;
    private String title;
    private Long quantity;
}
