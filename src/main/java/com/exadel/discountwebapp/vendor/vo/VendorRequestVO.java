package com.exadel.discountwebapp.vendor.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class VendorRequestVO extends VendorBaseVO {
    private Long locationId;
}
