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

    @NotBlank(message = "Title should not be empty")
    @Size(min = 2, max = 25, message = "Length of the title should be between 2 and 25")
    private String title;

    @Size(max = 510, message = "The maximum length of the imageUrl is 510")
    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$", message = "Incorrect imageUrl")
    private String imageUrl;
}
