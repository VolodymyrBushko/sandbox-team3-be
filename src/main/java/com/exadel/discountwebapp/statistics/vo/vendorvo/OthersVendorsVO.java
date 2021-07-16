package com.exadel.discountwebapp.statistics.vo.vendorvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OthersVendorsVO extends VendorVO{
    private String othersTitle;
    private Long othersQuantity;
}
