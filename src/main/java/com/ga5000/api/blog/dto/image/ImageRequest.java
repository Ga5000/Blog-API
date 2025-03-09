package com.ga5000.api.blog.dto.image;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ImageRequest(@NotNull(message = "File is required") MultipartFile file) {

}
