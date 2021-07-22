package com.exadel.discountwebapp.exception.exception.client;

import com.exadel.discountwebapp.exception.exception.base.CustomBaseException;
import lombok.Getter;

@Getter
public class EntityAlreadyUsedException extends CustomBaseException {

    public EntityAlreadyUsedException(Class clazz, String message) {
        super(clazz, message);
    }
}
