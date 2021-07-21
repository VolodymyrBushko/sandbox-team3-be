package com.exadel.discountwebapp.fileupload.image;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/image-upload")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ImageUploadResponse uploadImage(@RequestParam("image") MultipartFile image) {
        return imageUploadService.upload(image);
    }

    @DeleteMapping("/delete")
    public boolean deleteImage(@RequestBody String url) {
        return imageUploadService.delete(url);
    }
}
