package com.exadel.discountwebapp.vendor.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorBaseVO implements Serializable {
    private String title;
    private String description;
    private String imageUrl;
    private String email;
    private Long locationId;
}
