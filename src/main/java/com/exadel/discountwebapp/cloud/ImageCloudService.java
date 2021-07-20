package com.exadel.discountwebapp.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImageCloudService {

    private final Cloudinary cloudinary;

    private final int IMAGE_WIDTH = 200;
    private final int IMAGE_HEIGHT = 200;
    private final int PUBLIC_ID_GROUP = 3;

    private final String IMAGE_URL_TYPE = "secure_url";
    private final String SUCCESSFUL_DESTROY_STATUS = "ok";

    private static final String CLOUDINARY_IMAGE_URL_REGEXP = "/(.*)([\\/](\\w+))(\\.(jpg|png|gif|jpeg))";
    private static final Pattern pattern = Pattern.compile(CLOUDINARY_IMAGE_URL_REGEXP);

    @Autowired
    public ImageCloudService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String upload(MultipartFile file) {
        try {
            Transformation transformation = new Transformation().width(IMAGE_WIDTH).height(IMAGE_HEIGHT).crop("scale");
            Map response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("transformation", transformation));
            return response.get(IMAGE_URL_TYPE).toString();
        } catch (IOException ex) {
            return null;
        }
    }

    public boolean destroy(String url) {
        String publicId = extractPublicIdFromUrl(url);
        try {
            Map response = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
            String result = response.get("result").toString();
            return result.equalsIgnoreCase(SUCCESSFUL_DESTROY_STATUS);
        } catch (IOException ex) {
            return false;
        }
    }

    public String extractPublicIdFromUrl(String url) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(PUBLIC_ID_GROUP);
        }
        return null;
    }
}
