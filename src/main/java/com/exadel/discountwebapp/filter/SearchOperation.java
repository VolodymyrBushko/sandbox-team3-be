package com.exadel.discountwebapp.filter;

import com.exadel.discountwebapp.exception.exception.client.NotAllowedOperationException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SearchOperation {
    EQUALITY(":"),
    NOT_EQUALITY("!:"),
    STARTS_WITH(":*"),
    ENDS_WITH("*:"),
    CONTAINS("*:*"),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    IN("~");

    private final String operation;

    SearchOperation(String operation) {
        this.operation = operation;
    }

    public static SearchOperation getOperation(String input) {
        return Arrays.stream(values())
                .filter(e -> e.operation.equals(input))
                .findFirst()
                .orElseThrow(() -> new NotAllowedOperationException(input));
    }
}
