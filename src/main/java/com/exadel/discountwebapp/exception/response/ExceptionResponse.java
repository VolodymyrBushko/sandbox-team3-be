package com.exadel.discountwebapp.exception.response;

import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
public class ExceptionResponse implements Serializable {
    private final String code;
    private final String message;
}
