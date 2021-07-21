package com.exadel.discountwebapp.baseclasses;

import org.springframework.stereotype.Component;

public interface BaseEntityMapper<ENTITY, RESPONSE_VO> {
    RESPONSE_VO toVO(ENTITY entity);
}
