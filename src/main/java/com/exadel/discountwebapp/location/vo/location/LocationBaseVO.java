package com.exadel.discountwebapp.location.vo.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationBaseVO implements Serializable {
    @NotBlank
    @Size(min = 2, max = 50)
    private String countryCode;
    @NotBlank
    @Size(min = 2, max = 50)
    private String city;
    @NotBlank
    @Size(min = 2, max = 255)
    private String addressLine;
}
