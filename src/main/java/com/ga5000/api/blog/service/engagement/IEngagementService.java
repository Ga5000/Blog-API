package com.ga5000.api.blog.service.engagement;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.post.Post;

public interface IEngagementService {
    void likeOrDislikePost(boolean likeOrDislike, Post post);
    void likeOrDislikeComment(boolean likeOrDislike, Comment comment);

}
