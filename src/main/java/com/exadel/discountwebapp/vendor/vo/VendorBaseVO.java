package com.exadel.discountwebapp.vendor.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorBaseVO implements Serializable {

    protected String title;
    protected String description;
    protected String imageUrl;
    protected String email;
    protected Long locationId;
}
