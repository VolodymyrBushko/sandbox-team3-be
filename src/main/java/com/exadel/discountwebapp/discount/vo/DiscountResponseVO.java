package com.exadel.discountwebapp.discount.vo;

import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DiscountResponseVO extends DiscountBaseVO {

    @NotNull
    private Long id;

    @NotNull
    private CategoryResponseVO category;

    @NotNull
    private VendorResponseVO vendor;

    @NotNull
    private List<LocationResponseVO> locations;
}
