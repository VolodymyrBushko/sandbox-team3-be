package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.CustomBaseException;
import lombok.Getter;

@Getter
public class ParseException extends CustomBaseException {

    private static final String MESSAGE_PATTERN = "Could not parse in class %s field %s with value: %s";

    public ParseException(Class clazz, String fieldName, Object value) {
        super(clazz, fieldName, value, MESSAGE_PATTERN);
    }
}
