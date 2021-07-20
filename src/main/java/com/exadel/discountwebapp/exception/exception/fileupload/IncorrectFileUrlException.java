package com.exadel.discountwebapp.exception.exception.fileupload;

public class IncorrectFileUrlException extends RuntimeException {

    private static final String MESSAGE_PATTERN = "The URL is incorrect: %s";

    public IncorrectFileUrlException(String url) {
        super(String.format(MESSAGE_PATTERN, url));
    }
}
