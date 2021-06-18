package com.exadel.discountwebapp.vendor.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class VendorRequestVO extends VendorBaseVO {

    @NotNull
    private Long locationId;
}
