package com.ga5000.api.blog.domain.image;

import com.ga5000.api.blog.domain.post.Post;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    private String objectId;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL)
    private Post post;
}
