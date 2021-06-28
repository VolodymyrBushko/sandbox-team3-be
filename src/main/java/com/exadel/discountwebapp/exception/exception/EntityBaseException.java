package com.exadel.discountwebapp.exception.exception;

import lombok.Getter;

@Getter
public abstract class EntityBaseException extends RuntimeException {
    private final String className;
    private final String fieldName;

    protected EntityBaseException(String className, String fieldName, Object value, String messagePattern) {
        super(String.format(messagePattern, className, fieldName, value));

        this.className = className;
        this.fieldName = fieldName;
    }
}
