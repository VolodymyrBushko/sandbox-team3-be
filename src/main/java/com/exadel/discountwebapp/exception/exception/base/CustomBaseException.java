package com.exadel.discountwebapp.exception.exception.base;

import lombok.Getter;

@Getter
public abstract class CustomBaseException extends RuntimeException {

    private Class clazz;
    private String fieldName;
    private Object fieldValue;

    protected CustomBaseException(Class clazz, String fieldName, Object fieldValue, String messagePattern) {
        super(String.format(messagePattern, clazz.getSimpleName(), fieldName, fieldValue));

        this.clazz = clazz;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    protected CustomBaseException(Class clazz, String message) {
        super(message);
        this.clazz = clazz;
    }
}
