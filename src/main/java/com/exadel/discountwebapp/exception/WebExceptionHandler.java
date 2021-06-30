package com.exadel.discountwebapp.exception;

import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.exception.exception.client.IncorrectFilterInputException;
import com.exadel.discountwebapp.exception.exception.client.ParseException;
import com.exadel.discountwebapp.exception.response.ExceptionResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class WebExceptionHandler {

    private static final String AUTHORIZED_EXCEPTION_CODE = "UNAUTHORIZED";
    private static final String GLOBAL_EXCEPTION_CODE = "INTERNAL_SERVER_ERROR";
    private static final String GLOBAL_EXCEPTION_MESSAGE = "Something is wrong";

    private static final String RESPONSE_CODE_PATTERN = "%s.%s.%s";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = UNPROCESSABLE_ENTITY)
    public ExceptionResponse unprocessableEntityException(MethodArgumentNotValidException ex) {
        FieldError field = ex.getBindingResult().getFieldError();
        String code = String.format(RESPONSE_CODE_PATTERN, field.getObjectName(), field.getField(), UNPROCESSABLE_ENTITY.value());
        return new ExceptionResponse(code, field.getDefaultMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public ExceptionResponse notFoundException(EntityNotFoundException ex) {
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClazz().getSimpleName(), ex.getFieldName(), NOT_FOUND.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(value = IncorrectFilterInputException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ExceptionResponse badRequestException(IncorrectFilterInputException ex) {
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClazz().getSimpleName(), ex.getFieldName(), BAD_REQUEST.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(value = UNPROCESSABLE_ENTITY)
    public ExceptionResponse unprocessableEntityParseException(ParseException ex) {
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClazz().getSimpleName(), ex.getFieldName(), UNPROCESSABLE_ENTITY.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(value = CONFLICT)
    public ExceptionResponse entityAlreadyExistsException(EntityAlreadyExistsException ex) {
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClazz().getSimpleName(), ex.getFieldName(), UNPROCESSABLE_ENTITY.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = UNAUTHORIZED)
    public ExceptionResponse badCredentialsException(BadCredentialsException ex) {
        return new ExceptionResponse(AUTHORIZED_EXCEPTION_CODE, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ExceptionResponse globalException() {
        return new ExceptionResponse(GLOBAL_EXCEPTION_CODE, GLOBAL_EXCEPTION_MESSAGE);
    }
}
