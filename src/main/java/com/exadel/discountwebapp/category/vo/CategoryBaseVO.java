package com.exadel.discountwebapp.category.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CategoryBaseVO implements Serializable {

    protected String title;
    protected String imageUrl;
}
