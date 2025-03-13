package com.ga5000.api.blog.dto.post.search;


import com.ga5000.api.blog.dto.image.ImageResponse;
import com.ga5000.api.blog.dto.user.AuthorResponse;

import java.time.LocalDate;
import java.util.UUID;

public record PostSearchResponse(UUID postId,
                                 ImageResponse image,
                                 String title,
                                 LocalDate createdAt,
                                 LocalDate updatedAt,
                                 AuthorResponse author) {
}
