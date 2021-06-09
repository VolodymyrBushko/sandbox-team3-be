package com.exadel.discountwebapp.location.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseVO extends LocationBaseVO {
    private Long id;
}
