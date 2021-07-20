package com.exadel.discountwebapp.cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/cloud")
@RequiredArgsConstructor
public class CloudController {

    private final ImageCloudService imageCloudService;

    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("image") MultipartFile image) {
        return imageCloudService.upload(image);
    }

    @DeleteMapping("/destroy/image")
    public boolean destroyImage(@RequestBody String url) {
        return imageCloudService.destroy(url);
    }
}
