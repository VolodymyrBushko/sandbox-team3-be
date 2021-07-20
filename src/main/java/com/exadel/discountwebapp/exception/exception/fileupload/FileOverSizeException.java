package com.exadel.discountwebapp.exception.exception.fileupload;

public class FileOverSizeException extends RuntimeException {

    private static final String MESSAGE_PATTERN = "The file size is over max size for file: %s";

    public FileOverSizeException(String filename) {
        super(String.format(MESSAGE_PATTERN, filename));
    }
}
