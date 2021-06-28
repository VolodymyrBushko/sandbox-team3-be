package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.ClientBaseException;
import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends ClientBaseException {

    private static final String MESSAGE_PATTERN = "%s already exists with %s: %s";

    public EntityAlreadyExistsException(String className, String fieldName, Object value) {
        super(className, fieldName, value, MESSAGE_PATTERN);
    }
}
