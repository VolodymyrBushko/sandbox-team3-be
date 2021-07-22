package com.exadel.discountwebapp.common;

public interface BaseEntityMapper<ENTITY, RESPONSE_VO> {
    RESPONSE_VO toVO(ENTITY entity);
}