package com.ga5000.api.blog.dto.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record PostRequest(@NotEmpty(message = "Title is required") String title,
                          @NotEmpty(message = "Content is required") String content,
                          @Size(min = 1, message = "At least one category is required")
                          List<UUID> categories) {
}
