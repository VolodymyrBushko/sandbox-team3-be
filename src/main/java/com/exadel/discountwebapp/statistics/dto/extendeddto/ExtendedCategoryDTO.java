package com.exadel.discountwebapp.statistics.dto.extendeddto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedCategoryDTO {
    private String title;
    private long quantity;
}
