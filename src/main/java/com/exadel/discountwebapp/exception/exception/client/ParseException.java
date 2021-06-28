package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.ClientInputBaseException;
import lombok.Getter;

@Getter
public class ParseException extends ClientInputBaseException {

    private static final String MESSAGE_PATTERN = "Could not parse in class %s field %s with value: %s";

    public ParseException(String className, String fieldName, Object value) {
        super(className, fieldName, value, MESSAGE_PATTERN);
    }
}
