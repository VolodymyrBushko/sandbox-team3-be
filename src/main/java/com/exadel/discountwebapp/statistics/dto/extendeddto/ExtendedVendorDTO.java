package com.exadel.discountwebapp.statistics.dto.extendeddto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedVendorDTO {
    private String title;
    private String description;
    private String email;
    private long quantity;
}
