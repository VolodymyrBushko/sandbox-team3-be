package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.ClientBaseException;
import lombok.Getter;

@Getter
public class IncorrectFilterInputException extends ClientBaseException {

    private static final String MESSAGE_PATTERN = "Could not find in class %s field %s";

    public IncorrectFilterInputException(String className, String fieldName, Object value) {
        super(className, fieldName, value, MESSAGE_PATTERN);
    }
}
