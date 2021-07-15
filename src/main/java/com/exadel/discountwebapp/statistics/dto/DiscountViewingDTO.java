package com.exadel.discountwebapp.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountViewingDTO {
    private Long id;
    private String title;
    private Long quantity;
}
