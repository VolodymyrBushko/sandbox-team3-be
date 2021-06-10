package com.exadel.discountwebapp.category.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class CategoryBaseVO implements Serializable {

    @NotBlank
    @Size(min = 2, max = 25)
    private String title;

    @Size(max = 510)
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String imageUrl;
}
