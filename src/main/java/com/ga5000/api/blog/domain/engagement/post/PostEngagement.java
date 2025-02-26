package com.ga5000.api.blog.domain.engagement.post;

import com.ga5000.api.blog.domain.engagement.Engagement;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "post_engagement")
public class PostEngagement extends Engagement {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "postId", nullable = false)
    private Post post;

    public PostEngagement(User user, boolean isLike, Post post) {
        super(user, isLike);
        this.post = post;
    }

    public PostEngagement() {
        super();
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
