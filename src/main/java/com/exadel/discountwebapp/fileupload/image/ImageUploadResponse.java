package com.exadel.discountwebapp.fileupload.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadResponse {

    @NotBlank
    private String url;

    @NotBlank
    private String publicId;

    @NotBlank
    private String extension;

    @NotNull
    @Positive
    private Long size;
}
