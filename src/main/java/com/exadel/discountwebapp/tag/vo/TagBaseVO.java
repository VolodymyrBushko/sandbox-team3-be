package com.exadel.discountwebapp.tag.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class TagBaseVO implements Serializable {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}