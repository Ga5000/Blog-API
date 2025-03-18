package com.ga5000.api.blog.domain.comment;

import com.ga5000.api.blog.domain.engagement.comment.CommentEngagement;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comments_tb")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentId;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int replyCount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Comment(String content, LocalDateTime updatedAt, int replyCount, User author,
                   Post post, List<CommentEngagement> commentEngagements) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.replyCount = replyCount;
        this.author = author;
        this.post = post;
        this.commentEngagements = commentEngagements;
    }

    public Comment(String content, Post post, User author) {
        this.content = content;
        this.post = post;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public Comment(Comment parentComment, String content, User author) {
        this.parentComment = parentComment;
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
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