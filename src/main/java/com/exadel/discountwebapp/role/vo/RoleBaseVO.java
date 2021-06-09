package com.exadel.discountwebapp.role.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class RoleBaseVO implements Serializable {

    @NotNull(message = "Name cannot be null")
    private String name;
}
