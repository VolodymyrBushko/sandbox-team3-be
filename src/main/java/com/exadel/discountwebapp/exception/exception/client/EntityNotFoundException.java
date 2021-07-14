package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.CustomBaseException;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends CustomBaseException {

    private static final String MESSAGE_PATTERN = "Could not find %s with %s: %s";

    public EntityNotFoundException(Class clazz, String fieldName, Object value) {
        super(clazz, fieldName, value, MESSAGE_PATTERN);
    }

    public EntityNotFoundException(Class clazz, String message) {
        super(clazz, message);
    }
}
