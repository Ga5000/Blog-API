package com.ga5000.api.blog.service.engagement;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.Engagement;
import com.ga5000.api.blog.domain.engagement.comment.CommentEngagement;
import com.ga5000.api.blog.domain.engagement.post.PostEngagement;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.repository.engagement.comment.CommentEngagementRepository;
import com.ga5000.api.blog.repository.engagement.post.PostEngagementRepository;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class EngagementService implements IEngagementService {
    private final PostEngagementRepository postEngagementRepository;
    private final CommentEngagementRepository commentEngagementRepository;
    private final AuthService authService;
    private final UserService userService;

    public EngagementService(PostEngagementRepository postEngagementRepository,
                             CommentEngagementRepository commentEngagementRepository,
                             AuthService authService, UserService userService) {
        this.postEngagementRepository = postEngagementRepository;
        this.commentEngagementRepository = commentEngagementRepository;
        this.authService = authService;
        this.userService = userService;
    }


    @Transactional
    @Override
    public void likeOrDislikePost(boolean likeOrDislike, Post post) {
        User user = userService.getUserById(authService.getCurrentUserId());

        Optional<PostEngagement> optionalEngagement = postEngagementRepository
                .findByUserIdAndPostId(user.getUserId(), post.getPostId());

        optionalEngagement.ifPresentOrElse(postEngagement -> processEngagement(postEngagement, likeOrDislike,
                engagement -> postEngagementRepository.save((PostEngagement) engagement),
                engagement -> postEngagementRepository.delete((PostEngagement) engagement)),
                () -> {
                    PostEngagement newPostEngagement = new PostEngagement(user, post);
                    setEngagementCounts(newPostEngagement, likeOrDislike);
                    postEngagementRepository.save(newPostEngagement);
                });

    }

    @Transactional
    @Override
    public void likeOrDislikeComment(boolean likeOrDislike, Comment comment) {
        User user = userService.getUserById(authService.getCurrentUserId());
        Optional<CommentEngagement> optionalEngagement = commentEngagementRepository
                .findByUserIdAndCommentId(user.getUserId(), comment.getCommentId());

        optionalEngagement.ifPresentOrElse(commentEngagement ->
                        processEngagement(commentEngagement, likeOrDislike,
                engagement -> commentEngagementRepository.save((CommentEngagement) engagement),
                engagement -> commentEngagementRepository.delete((CommentEngagement) engagement)),
                () -> {
                    CommentEngagement newEngagement = new CommentEngagement(user, comment);
                    setEngagementCounts(newEngagement, likeOrDislike);
                    commentEngagementRepository.save(newEngagement);
                });
    }


    private void processEngagement(Engagement engagement, boolean likeOrDislike,
                                   Consumer<Engagement> saveMethod, Consumer<Engagement> deleteMethod) {
        if (likeOrDislike) {
            if (engagement.getLikeCount() > 0) {
                deleteMethod.accept(engagement);
            } else {
                engagement.setLikeCount(1);
                saveMethod.accept(engagement);
            }
        } else {
            if (engagement.getDislikeCount() > 0) {
                deleteMethod.accept(engagement);
            } else {
                engagement.setDislikeCount(1);
                saveMethod.accept(engagement);
            }
        }

    }

    private void setEngagementCounts(Engagement engagement, boolean likeOrDislike) {
        if (likeOrDislike) {
            engagement.setLikeCount(1);
        } else {
            engagement.setDislikeCount(1);
        }
    }
}
