package com.exadel.discountwebapp.location.vo.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationBaseVO implements Serializable {
    @NotBlank
    @Size(min = 2, max = 50)
    private String country;
    @NotBlank
    @Size(min = 2, max = 50)
    private String city;
}
