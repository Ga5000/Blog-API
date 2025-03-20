package com.ga5000.api.blog.dto.post;

import jakarta.validation.constraints.NotEmpty;

public record UpdatePostContentRequest(@NotEmpty(message = "New content is required") String newContent) {
}
