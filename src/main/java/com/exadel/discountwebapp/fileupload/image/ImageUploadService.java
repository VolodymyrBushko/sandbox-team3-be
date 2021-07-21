package com.exadel.discountwebapp.fileupload.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.exadel.discountwebapp.exception.exception.fileupload.FileDestroyException;
import com.exadel.discountwebapp.exception.exception.fileupload.FileUploadException;
import com.exadel.discountwebapp.exception.exception.fileupload.IncorrectFileUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@SuppressWarnings("unchecked")
public class ImageUploadService {

    private final Cloudinary cloudinary;
    private final ImageUploadValidator imageValidator;

    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 200;

    private static final int PUBLIC_ID_GROUP = 3;
    private static final int EXTENSION_GROUP = 4;

    private static final String IMAGE_URL_TYPE = "secure_url";
    private static final String SUCCESSFUL_DESTROY_STATUS = "ok";
    private static final String EXCEPTION_MESSAGE_PATTERN = "Failed to handle %s. Exception message: %s";

    private static final String CLOUDINARY_IMAGE_URL_REGEXP = "/(.*)([\\/](\\w+))(\\.(jpg|png|gif|jpeg))";
    private static final Pattern PATTERN = Pattern.compile(CLOUDINARY_IMAGE_URL_REGEXP);

    @Autowired
    public ImageUploadService(Cloudinary cloudinary, ImageUploadValidator imageValidator) {
        this.cloudinary = cloudinary;
        this.imageValidator = imageValidator;
    }

    public ImageUploadResponse upload(MultipartFile image) {
        try {
            imageValidator.validate(image);
            Transformation transformation = new Transformation().width(IMAGE_WIDTH).height(IMAGE_HEIGHT).crop("scale");
            Map cloudResponse = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("transformation", transformation));
            String url = cloudResponse.get(IMAGE_URL_TYPE).toString();
            return getImageUploadResponse(url, image.getSize());
        } catch (IOException ex) {
            String filename = image.getName();
            log.error(String.format(EXCEPTION_MESSAGE_PATTERN, filename, ex.getMessage()));
            throw new FileUploadException(filename);
        }
    }

    public boolean delete(String url) {
        String publicId = extractFromUrl(url, PUBLIC_ID_GROUP);
        try {
            Map response = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
            String result = response.get("result").toString();
            return result.equalsIgnoreCase(SUCCESSFUL_DESTROY_STATUS);
        } catch (IOException ex) {
            log.error(String.format(EXCEPTION_MESSAGE_PATTERN, url, ex.getMessage()));
            throw new FileDestroyException(url);
        }
    }

    private String extractFromUrl(String url, int group) {
        Matcher matcher = PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(group);
        }
        throw new IncorrectFileUrlException(url);
    }

    private ImageUploadResponse getImageUploadResponse(String url, long size) {
        return ImageUploadResponse.builder()
                .publicId(extractFromUrl(url, PUBLIC_ID_GROUP))
                .extension(extractFromUrl(url, EXTENSION_GROUP))
                .size(size)
                .url(url)
                .build();
    }
}
