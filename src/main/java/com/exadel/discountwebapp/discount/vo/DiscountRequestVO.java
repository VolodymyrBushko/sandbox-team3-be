package com.exadel.discountwebapp.discount.vo;

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
public class DiscountRequestVO extends DiscountBaseVO {

    @NotNull
    private Long categoryId;

    @NotNull
    private Long vendorId;

    @NotEmpty
    private List <Long> tags;
}
