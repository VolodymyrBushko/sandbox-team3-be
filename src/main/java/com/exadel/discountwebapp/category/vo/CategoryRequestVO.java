package com.exadel.discountwebapp.category.vo;

import com.exadel.discountwebapp.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestVO extends CategoryBaseVO {

    public static Category toCategory(CategoryRequestVO request) {
        return Category.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .build();
    }
}
