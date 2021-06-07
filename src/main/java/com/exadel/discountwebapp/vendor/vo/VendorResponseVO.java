package com.exadel.discountwebapp.vendor.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseVO extends VendorBaseVO {
    private Long id;
    private Long locationId;
}
