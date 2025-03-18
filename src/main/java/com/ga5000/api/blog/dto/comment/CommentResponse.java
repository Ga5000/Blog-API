package com.ga5000.api.blog.dto.comment;
import com.ga5000.api.blog.dto.user.AuthorResponse;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(UUID commentId,
                              String content,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              AuthorResponse author,
                              int replyCount,
                              int likeCount,
                              int dislikeCount) {
}
