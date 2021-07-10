package com.exadel.discountwebapp.exception.exception.notification;

import com.exadel.discountwebapp.exception.exception.base.CustomBaseException;
import lombok.Getter;

@Getter
public class EntityAlreadySubscribedException extends CustomBaseException {

    private static final String MESSAGE_PATTERN = "The %s with %s: %s is already subscribed";

    public EntityAlreadySubscribedException(Class clazz, String fieldName, Object fieldValue) {
        super(clazz, fieldName, fieldValue, MESSAGE_PATTERN);
    }
}
