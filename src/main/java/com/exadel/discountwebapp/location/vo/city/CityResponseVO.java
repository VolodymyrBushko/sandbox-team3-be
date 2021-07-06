package com.exadel.discountwebapp.location.vo.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponseVO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String city;
}
