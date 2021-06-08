package com.exadel.discountwebapp.role.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class RoleBaseVO implements Serializable {

    protected String name;
}
