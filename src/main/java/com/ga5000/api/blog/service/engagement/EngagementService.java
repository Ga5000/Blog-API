package com.ga5000.api.blog.service.engagement;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.repository.engagement.comment.CommentEngagementRepository;
import com.ga5000.api.blog.repository.engagement.post.PostEngagementRepository;
import org.springframework.stereotype.Service;

@Service
public class EngagementService implements IEngagementService {
    private final PostEngagementRepository postEngagementRepository;
    private final CommentEngagementRepository commentEngagementRepository;

    public EngagementService(PostEngagementRepository postEngagementRepository, CommentEngagementRepository commentEngagementRepository) {
        this.postEngagementRepository = postEngagementRepository;
        this.commentEngagementRepository = commentEngagementRepository;
    }

    @Override
    public void likeOrDislikePost(boolean likeOrDislike, Post post) {

    }

    @Override
    public void likeOrDislikeComment(boolean likeOrDislike, Comment comment) {

    }
}
