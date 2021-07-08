package com.exadel.discountwebapp.discount.vo;

import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
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

    @NotEmpty
    private List<LocationResponseVO> locations;

    @NotEmpty
    private List<TagResponseVO> tags;
}
