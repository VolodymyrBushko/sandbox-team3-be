package com.exadel.discountwebapp.exception;

import com.exadel.discountwebapp.exception.exception.EntityAlreadyExistsException;
import com.exadel.discountwebapp.exception.exception.EntityNotFoundException;
import com.exadel.discountwebapp.exception.exception.IncorrectFilterInputException;
import com.exadel.discountwebapp.exception.exception.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class WebExceptionHandler {

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
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClassName(), ex.getFieldName(), NOT_FOUND.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(value = IncorrectFilterInputException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ExceptionResponse badRequestException(IncorrectFilterInputException ex) {
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClassName(), ex.getFieldName(), BAD_REQUEST.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(value = UNPROCESSABLE_ENTITY)
    public ExceptionResponse unprocessableEntityParseException(ParseException ex) {
        String code = String.format(RESPONSE_CODE_PATTERN, ex.getClassName(), ex.getFieldName(), UNPROCESSABLE_ENTITY.value());
        return new ExceptionResponse(code, ex.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public String entityAlreadyExistsException(EntityAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String globalException() {
        return "Something is wrong";
    }
}
