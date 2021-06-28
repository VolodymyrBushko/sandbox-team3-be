package com.exadel.discountwebapp.exception.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends ClientInputBaseException {

    private static final String MESSAGE_PATTERN = "Could not find %s with %s: %s";

    public EntityNotFoundException(String className, String fieldName, Object value) {
        super(className, fieldName, value, MESSAGE_PATTERN);
    }
}
