package com.exadel.discountwebapp.vendor.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class VendorRequestVO extends VendorBaseVO {

    @NotEmpty
    private List<Long> locationIds;
}
