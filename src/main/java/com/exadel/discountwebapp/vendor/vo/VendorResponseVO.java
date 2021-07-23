package com.exadel.discountwebapp.vendor.vo;

import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseVO extends VendorBaseVO {

    @NotNull
    private Long id;

    @NotEmpty
    private List<LocationResponseVO> locations;
}