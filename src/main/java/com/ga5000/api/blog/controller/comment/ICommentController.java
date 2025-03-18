package com.ga5000.api.blog.controller.comment;

import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public interface ICommentController {
    ResponseEntity<Map<String, String>> deleteComment(UUID commentId);
    ResponseEntity<Map<String, String>> updateCommentContent(UUID commentId, CommentRequest request);
    ResponseEntity<Map<String, String>> replyToComment(UUID commentId, CommentRequest request);
    ResponseEntity<Void> likeOrDislikeComment(UUID commentId, boolean likeOrDislike);
    ResponseEntity<PagedModel<EntityModel<CommentResponse>>> getCommentReplies(UUID commentId, Pageable pageable);
}
