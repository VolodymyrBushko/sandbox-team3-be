package com.exadel.discountwebapp.fileupload.image;

import com.exadel.discountwebapp.exception.exception.fileupload.FileEmptyException;
import com.exadel.discountwebapp.exception.exception.fileupload.FileOverSizeException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploadValidator {

    private final long MAX_IMAGE_SIZE = 30000L;

    public void validate(MultipartFile image) {
        checkEmpty(image);
        checkSize(image);
    }

    private void checkEmpty(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new FileEmptyException();
        }
    }

    private void checkSize(MultipartFile image) {
        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new FileOverSizeException(image.getName());
        }
    }
}
