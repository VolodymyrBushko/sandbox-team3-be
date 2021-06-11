package com.exadel.discountwebapp.location.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationBaseVO implements Serializable {
    private String country;
    private String city;
}
