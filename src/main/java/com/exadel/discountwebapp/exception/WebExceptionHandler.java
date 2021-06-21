package com.exadel.discountwebapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public String methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError field = ex.getBindingResult().getFieldError();
        return String.format("%s.%s.%s", field.getObjectName(), field.getField(), field.getDefaultMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String entityNotFoundException(EntityNotFoundException ex) {
        return ex.getMessage();
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
