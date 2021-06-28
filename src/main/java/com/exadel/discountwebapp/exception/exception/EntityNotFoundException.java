package com.exadel.discountwebapp.exception.exception;

public class EntityNotFoundException extends EntityBaseException {

    public EntityNotFoundException(String className, String fieldName, Object value) {
        super(className, fieldName, value, "Could not find %s with %s: %s");
    }
}
