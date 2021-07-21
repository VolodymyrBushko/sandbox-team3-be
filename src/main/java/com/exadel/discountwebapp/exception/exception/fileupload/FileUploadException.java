package com.exadel.discountwebapp.exception.exception.fileupload;

public class FileUploadException extends RuntimeException {

    private static final String MESSAGE_PATTERN = "Could not upload file: %s";

    public FileUploadException(String filename) {
        super(String.format(MESSAGE_PATTERN, filename));
    }
}
