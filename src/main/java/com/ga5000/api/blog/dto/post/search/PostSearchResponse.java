package com.ga5000.api.blog.dto.post.search;

import com.ga5000.api.blog.dto.image.ImageResponse;
import com.ga5000.api.blog.dto.user.AuthorResponse;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record PostSearchResponse(UUID postId,
                                 ImageResponse image,
                                 String title,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt,
                                 AuthorResponse author) implements Serializable {
}
