package com.ga5000.api.blog.dto.comment;

import com.ga5000.api.blog.dto.user.AuthorResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CommentResponse(UUID commentId,
                              String content,
                              LocalDate createdAt,
                              LocalDate updatedAt,
                              AuthorResponse author,
                              int replyCount) {
}
