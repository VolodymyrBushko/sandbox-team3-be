package com.exadel.discountwebapp.vendor.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VendorBaseVO implements Serializable {

    @NotBlank
    @Size(min = 2, max = 50)
    private String title;

    @NotBlank
    @Size(min = 2, max = 510)
    private String description;

    @NotBlank
    @Size(min = 2, max = 510)
    private String imageUrl;

    @Email
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?" +
            "((\\(\\d{1,3}\\))" +
            "|\\d{1,3})[- .]?" +
            "\\d{3,4}[- .]?" +
            "(\\d{4}" +
            "|\\d{2}[- .]?" +
            "\\d{2})$")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;
}
