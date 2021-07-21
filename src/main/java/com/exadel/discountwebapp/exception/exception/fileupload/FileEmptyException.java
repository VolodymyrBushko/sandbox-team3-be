package com.exadel.discountwebapp.exception.exception.fileupload;

public class FileEmptyException extends RuntimeException {

    private static final String MESSAGE = "The file couldn't be empty";

    public FileEmptyException() {
        super(MESSAGE);
    }
}
