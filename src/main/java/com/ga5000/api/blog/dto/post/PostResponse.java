package com.ga5000.api.blog.dto.post;

import com.ga5000.api.blog.dto.image.ImageResponse;
import com.ga5000.api.blog.dto.user.AuthorResponse;
import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponse(UUID postId, String title,
                           ImageResponse image, String content,
                           LocalDateTime createdAt, LocalDateTime updatedAt,
                           AuthorResponse author,
                           int likeCount, int dislikeCount) {
}
