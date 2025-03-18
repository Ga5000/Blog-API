package com.ga5000.api.blog.domain.image;

import com.ga5000.api.blog.domain.post.Post;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "images_tb")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    @Column(nullable = false)
    private String objectId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post;

    public Image() {}

    public Image(String objectId, Post post) {
        this.objectId = objectId;
        this.post = post;
    }

    public UUID getImageId() {
        return imageId;
    }

    public String getObjectId() {
        return objectId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
