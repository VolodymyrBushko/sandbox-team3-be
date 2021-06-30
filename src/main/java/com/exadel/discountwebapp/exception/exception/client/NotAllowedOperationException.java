package com.exadel.discountwebapp.exception.exception.client;

import lombok.Getter;

@Getter
public class NotAllowedOperationException extends RuntimeException {

    private static final String MESSAGE_PATTERN = "Operation %s is not allowed";
    private final String operation;

    public NotAllowedOperationException(String operation) {
        super(String.format(MESSAGE_PATTERN, operation));
        this.operation = operation;
    }
}
