package com.ga5000.api.blog.domain.engagement;

import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID engagementId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private int likeCount; // if it's a dislike it's null

    private int dislikeCount; // if it's a like it's null

    public Engagement() {}

    public Engagement(User user, int likeCount, int dislikeCount) {
        this.user = user;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public Engagement(User user) {
        this.user = user;
    }

    public UUID getEngagementId() {
        return engagementId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
