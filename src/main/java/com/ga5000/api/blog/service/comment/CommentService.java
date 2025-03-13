package com.ga5000.api.blog.service.comment;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.repository.comment.CommentRepository;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.utils.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

@Service
public class CommentService implements ICommentService{
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final DtoMapper dtoMapper;

    public CommentService(CommentRepository commentRepository, AuthService authService, DtoMapper dtoMapper) {
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    @Override
    public void createComment(Post post, CommentRequest request) {
        User author = authService.getCurrentUser();
        var newComment = new Comment(request.content(), post, author);
        saveComment(newComment);

    }

    @Transactional
    @Override
    public void deleteComment(UUID commentId) {
        var existingComment = findCommentById(commentId);
        if(existingComment.getParentComment() != null) { // checks if the comment is a reply, if so decrease the reply count in the parent comment
            var parentComment = existingComment.getParentComment();
            parentComment.setReplyCount(parentComment.getReplyCount() - 1);
            saveComment(parentComment);
        }
        commentRepository.delete(existingComment);
    }

    @Transactional
    @Override
    public void updateCommentContent(UUID commentId, CommentRequest request) {
        var existingComment = findCommentById(commentId);
        existingComment.setContent(request.content());
        saveComment(existingComment);
    }

    @Transactional
    @Override
    public void replyComment(UUID commentId, CommentRequest request) {
        User author = authService.getCurrentUser();
        var existingComment = findCommentById(commentId);
        var reply = new Comment(existingComment, request.content(), author);
        existingComment.setReplyCount(existingComment.getReplyCount() + 1);
        saveComment(reply);
        saveComment(existingComment);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getCommentReplies(UUID commentId, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("createdAt").descending());
        Page<Comment> repliesPage = commentRepository.
                findByParentComment_CommentId(commentId, pageable);

        return repliesPage.map(dtoMapper::toCommentResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getPostComments(Post post, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize, Sort.by("createdAt").descending());
        Page<Comment> commentsPage = commentRepository.findCommentsByPost_PostId(post.getPostId(), pageable);

        return commentsPage.map(dtoMapper::toCommentResponse);
    }

    private void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    private Comment findCommentById(UUID commentId){
        return findByIdOrThrow(commentId, commentRepository, () -> new EntityNotFoundException("Comment not found"));
    }
}

