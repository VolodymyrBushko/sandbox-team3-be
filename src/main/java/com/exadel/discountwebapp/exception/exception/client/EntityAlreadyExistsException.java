package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.ClientBaseException;
import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends ClientBaseException {

    private static final String MESSAGE_PATTERN = "%s already exists with %s: %s";

    public EntityAlreadyExistsException(Class clazz, String fieldName, Object value) {
        super(clazz, fieldName, value, MESSAGE_PATTERN);
    }
}
