package com.exadel.discountwebapp.role.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class RoleBaseVO implements Serializable {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
}
