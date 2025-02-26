package com.ga5000.api.blog.domain.engagement;

import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class Engagement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isLike; // true = like / false = dislike

    public Engagement() {}

    public Engagement(User user, boolean isLike) {
        this.user = user;
        this.isLike = isLike;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
