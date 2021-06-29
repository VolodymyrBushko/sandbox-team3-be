package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.ClientBaseException;
import lombok.Getter;

@Getter
public class IncorrectFilterInputException extends ClientBaseException {

    private static final String MESSAGE_PATTERN = "Could not find in class %s field %s";

    public IncorrectFilterInputException(Class clazz, String fieldName, Object value) {
        super(clazz, fieldName, value, MESSAGE_PATTERN);
    }
}
