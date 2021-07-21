package com.exadel.discountwebapp.history.enums;

import lombok.Getter;

@Getter
public enum OperationsEnum {
    CHANGES("Changes");

    public final String getOperation;

    OperationsEnum(String getOperation) {
        this.getOperation = getOperation;
    }
}
