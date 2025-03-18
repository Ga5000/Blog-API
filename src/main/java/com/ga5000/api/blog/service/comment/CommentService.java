package com.ga5000.api.blog.service.comment;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.domain.user.role.Role;
import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.middleware.exception.auth.UnauthorizedOperationException;
import com.ga5000.api.blog.middleware.exception.entity.EntityNotFoundException;
import com.ga5000.api.blog.repository.comment.CommentRepository;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.engagement.EngagementService;
import com.ga5000.api.blog.service.user.UserService;
import com.ga5000.api.blog.utils.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

@Service
public class CommentService implements ICommentService{
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final UserService userService;
    private final EngagementService engagementService;
    private final DtoMapper dtoMapper;

    public CommentService(CommentRepository commentRepository, AuthService authService,
                          UserService userService, EngagementService engagementService, DtoMapper dtoMapper) {
        this.commentRepository = commentRepository;
        this.authService = authService;
        this.userService = userService;
        this.engagementService = engagementService;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    @Override
    public void createComment(Post post, CommentRequest request) {
        User author =  userService.getUserById(authService.getCurrentUserId());
        var newComment = new Comment(request.content(), post, author);
        saveComment(newComment);

    }

    @Transactional
    @Override
    public void deleteComment(UUID commentId) {
        var existingComment = findCommentById(commentId);
        isOwnerOrAdmin( userService.getUserById(authService.getCurrentUserId()), existingComment);
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
        isOwnerOrAdmin( userService.getUserById(authService.getCurrentUserId()), existingComment);
        existingComment.setContent(request.content());
        existingComment.setUpdatedAt(LocalDateTime.now());
        saveComment(existingComment);
    }

    @Transactional
    @Override
    public void replyComment(UUID commentId, CommentRequest request) {
        User author =  userService.getUserById(authService.getCurrentUserId());
        var existingComment = findCommentById(commentId);
        var reply = new Comment(existingComment, request.content(), author);
        existingComment.setReplyCount(existingComment.getReplyCount() + 1);
        saveComment(reply);
        saveComment(existingComment);

    }

    @Transactional
    @Override
    public void likeOrDislikeComment(UUID commentId, boolean likeOrDislike) {
        var existingComment = findCommentById(commentId);
        engagementService.likeOrDislikeComment(likeOrDislike, existingComment);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getCommentReplies(UUID commentId, Pageable pageable) {
        pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by("createdAt").descending());
        Page<Comment> repliesPage = commentRepository.
                findByParentComment_CommentId(commentId, pageable);

        return repliesPage.map(dtoMapper::toCommentResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getPostComments(UUID postId, Pageable pageable, Sort.Direction direction) {
         pageable = PageRequest.of(0, pageable.getPageSize(), Sort.by(direction, "createdAt"));
        Page<Comment> commentsPage = commentRepository.findCommentsByPost_PostId(postId, pageable);

        return commentsPage.map(dtoMapper::toCommentResponse);
    }

    private void saveComment(Comment comment){
        commentRepository.save(comment);
    }

    private Comment findCommentById(UUID commentId) throws EntityNotFoundException{
        return findByIdOrThrow(commentId, commentRepository, () -> new EntityNotFoundException("Comment not found"));
    }


    private void isOwnerOrAdmin(User user, Comment comment) throws UnauthorizedOperationException {
        if(!comment.getAuthor().equals(user) || !user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("You are not authorized to edit or delete this comment");
        }
    }
}

