package com.exadel.discountwebapp.baseclasses;

public interface BaseEntityMapper<ENTITY, RESPONSE_VO> {
    RESPONSE_VO toVO(ENTITY entity);
}