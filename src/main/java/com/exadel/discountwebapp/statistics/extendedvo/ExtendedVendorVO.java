package com.exadel.discountwebapp.statistics.extendedvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedVendorVO {
    private String title;
    private String description;
    private String email;
    private String phoneNumber;
    private Long quantity;
}
