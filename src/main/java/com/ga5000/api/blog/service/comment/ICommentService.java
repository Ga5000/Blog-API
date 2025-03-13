package com.ga5000.api.blog.service.comment;

import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ICommentService {
    void createComment(Post post, CommentRequest request);
    void deleteComment(UUID commentId);
    void updateCommentContent(UUID commentId, CommentRequest request);
    void replyComment(UUID commentId, CommentRequest request);
    Page<CommentResponse> getCommentReplies(UUID commentId, int pageSize);
    Page<CommentResponse> getPostComments(Post post, int pageSize);

}
