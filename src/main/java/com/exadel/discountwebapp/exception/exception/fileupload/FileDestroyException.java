package com.exadel.discountwebapp.exception.exception.fileupload;

public class FileDestroyException extends RuntimeException {

    private static final String MESSAGE_PATTERN = "Could not destroy file by URL: %s";

    public FileDestroyException(String filename) {
        super(String.format(MESSAGE_PATTERN, filename));
    }
}
