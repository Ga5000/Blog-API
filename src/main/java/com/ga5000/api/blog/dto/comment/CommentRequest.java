package com.ga5000.api.blog.dto.comment;

import jakarta.validation.constraints.NotEmpty;

public record CommentRequest(@NotEmpty(message = "Content is required") String content) {
}
