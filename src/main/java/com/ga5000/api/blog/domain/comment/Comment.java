package com.ga5000.api.blog.domain.comment;

import com.ga5000.api.blog.domain.engagement.comment.CommentEngagement;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentId;

    @Column(nullable = false)
    private String content;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEngagement> commentEngagements = new ArrayList<>();

    public Comment() {}

    public Comment(String content, LocalDate updatedAt, User author,
                   Post post, List<CommentEngagement> commentEngagements) {
        this.content = content;
        this.createdAt = LocalDate.now();
        this.updatedAt = updatedAt;
        this.author = author;
        this.post = post;
        this.commentEngagements = commentEngagements;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public List<CommentEngagement> getCommentEngagements() {
        return commentEngagements;
    }

    public void setCommentEngagements(List<CommentEngagement> commentEngagements) {
        this.commentEngagements = commentEngagements;
    }
}