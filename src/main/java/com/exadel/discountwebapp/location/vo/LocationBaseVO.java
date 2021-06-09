package com.exadel.discountwebapp.location.vo;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationBaseVO implements Serializable {
    private String country;
    private String city;
}
