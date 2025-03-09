package com.ga5000.api.blog.domain.engagement.post;

import com.ga5000.api.blog.domain.engagement.Engagement;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PostEngagement extends Engagement {

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post;

    public PostEngagement() {super();}

    public PostEngagement(User user, int likeCount, int dislikeCount, Post post) {
        super(user, likeCount, dislikeCount);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}