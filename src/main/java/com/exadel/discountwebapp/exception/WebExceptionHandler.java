package com.exadel.discountwebapp.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

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

    @ExceptionHandler(value = {
            InvalidDataAccessApiUsageException.class,
            NotAllowedOperationException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String badRequestException(Exception ex) {
        switch (ex.getClass().getSimpleName()) {
            case "InvalidDataAccessApiUsageException":
                int index = ex.getMessage().indexOf("]") + 1;
                return ex.getMessage().substring(0, index);
            case "NotAllowedOperationException":
                return ex.getMessage();
            default:
                return "Bad request";
        }
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public String userInputParseException(Exception ex) {
        switch (ex.getClass().getSimpleName()) {
            case "DateTimeParseException":
                String parsedString = ((DateTimeParseException) ex).getParsedString();
                return String.format("Could not parse [%s] in DateTime format", parsedString);
            default:
                return "Bad input";
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String globalException() {
        return "Something is wrong";
    }
}
