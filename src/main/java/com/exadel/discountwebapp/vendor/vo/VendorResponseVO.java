package com.exadel.discountwebapp.vendor.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseVO extends VendorBaseVO {
    private Long id;
}
