package com.exadel.discountwebapp.filter;

import com.exadel.discountwebapp.exception.exception.NotAllowedOperationException;

import java.util.Arrays;

public enum SearchOperation {
    EQUALITY(":"),
    STARTS_WITH(":*"),
    ENDS_WITH("*:"),
    CONTAINS("*:*"),
    LESS_THAN("<"),
    GREATER_THAN(">");

    private final String operation;

    SearchOperation(String operation) {
        this.operation = operation;
    }

    public static SearchOperation getOperation(String input) {
        return Arrays.stream(values())
                .filter(e -> e.operation.equals(input))
                .findFirst()
                .orElseThrow(() -> new NotAllowedOperationException(String.format("Operation [%s] is not allowed", input)));
    }
}
