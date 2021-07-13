package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.CustomBaseException;
import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends CustomBaseException {

    private static final String MESSAGE_PATTERN = "%s already exists with %s: %s";

    public EntityAlreadyExistsException(Class clazz, String fieldName, Object value) {
        super(clazz, fieldName, value, MESSAGE_PATTERN);
    }
}
