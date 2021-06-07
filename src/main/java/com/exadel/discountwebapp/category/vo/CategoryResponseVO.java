package com.exadel.discountwebapp.category.vo;

import com.exadel.discountwebapp.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseVO extends CategoryBaseVO {

    private long id;

    public static CategoryResponseVO fromCategory(Category category) {
        return CategoryResponseVO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .imageUrl(category.getImageUrl())
                .build();
    }
}
