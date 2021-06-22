package com.exadel.discountwebapp.filter;

import java.util.List;

public enum SearchOperation {
    EQUALITY, STARTS_WITH, ENDS_WITH, CONTAINS, LESS_THAN, GREATER_THAN;

    private static final List<String> OPERATIONS = List.of(":", ":*", "*:", "*:*", "<", ">");

    public static SearchOperation getOperation(String input) {
        int index = OPERATIONS.indexOf(input);
        return index != -1
                ? SearchOperation.values()[index]
                : null;
    }
}
