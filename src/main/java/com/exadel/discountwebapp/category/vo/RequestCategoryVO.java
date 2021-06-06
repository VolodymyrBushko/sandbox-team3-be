package com.exadel.discountwebapp.category.vo;

import com.exadel.discountwebapp.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestCategoryVO extends BaseCategoryVO {

    public static Category toCategory(RequestCategoryVO request) {
        return Category.builder()
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .build();
    }
}
