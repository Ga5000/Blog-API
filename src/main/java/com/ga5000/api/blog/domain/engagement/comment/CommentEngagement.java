package com.ga5000.api.blog.domain.engagement.comment;


import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.Engagement;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment_engagements_tb")
public class CommentEngagement extends Engagement {

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "commentId")
    private Comment comment;

    public CommentEngagement() {super();}

    public CommentEngagement(User user, int likeCount, int dislikeCount, Comment comment) {
        super(user, likeCount, dislikeCount);
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}