package com.exadel.discountwebapp.exception.exception.base;

import lombok.Getter;

@Getter
public abstract class ClientBaseException extends RuntimeException {

    private Class clazz;
    private String fieldName;

    protected ClientBaseException(Class clazz, String fieldName, Object value, String messagePattern) {
        super(String.format(messagePattern, clazz.getSimpleName(), fieldName, value));

        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    public ClientBaseException(String fieldName) {
        this.fieldName = fieldName;
    }
}
