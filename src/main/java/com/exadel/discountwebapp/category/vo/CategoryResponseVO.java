package com.exadel.discountwebapp.category.vo;

import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryResponseVO extends CategoryBaseVO {

    @NotNull
    private Long id;

    @NotNull
    private List<TagResponseVO> tags;
}
