package com.ga5000.api.blog.dto.post;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record PostRequest(@NotEmpty(message = "Title is required") String title,
                          @NotEmpty(message = "Content is required") String content,
                          @NotNull(message = "Image is required") MultipartFile file,
                          @Size(min = 1, message = "At least one category is required")
                          List<UUID> categories) {
}
