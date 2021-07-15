package com.exadel.discountwebapp.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorVO {
    private Long id;
    private String title;

    public VendorVO(String title) {
        this.title = title;
    }
}
