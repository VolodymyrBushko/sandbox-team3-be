package com.exadel.discountwebapp.vendor.vo;

import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseVO extends VendorBaseVO {
    private Long id;

    private LocationResponseVO location;
}