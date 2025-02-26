package com.ga5000.api.blog.domain.engagement.comment;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.Engagement;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "comment_engagement")
public class CommentEngagement extends Engagement {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "commentId", nullable = false)
    private Comment comment;

    public CommentEngagement(User user, boolean isLike, Comment comment) {
        super(user, isLike);
        this.comment = comment;
    }

    public CommentEngagement() {
        super();
    }
}
