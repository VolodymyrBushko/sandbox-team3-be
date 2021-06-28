package com.exadel.discountwebapp.exception.exception.base;

import lombok.Getter;

@Getter
public abstract class ClientBaseException extends RuntimeException {

    private final String className;
    private final String fieldName;

    protected ClientBaseException(String className, String fieldName, Object value, String messagePattern) {
        super(String.format(messagePattern, className, fieldName, value));

        this.className = className;
        this.fieldName = fieldName;
    }
}
