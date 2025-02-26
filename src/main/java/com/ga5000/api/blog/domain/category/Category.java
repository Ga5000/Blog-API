package com.ga5000.api.blog.domain.category;

import com.ga5000.api.blog.domain.post.Post;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
}
