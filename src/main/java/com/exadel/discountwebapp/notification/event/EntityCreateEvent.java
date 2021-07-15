package com.exadel.discountwebapp.notification.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EntityCreateEvent<T> {

    private final T entity;
}
