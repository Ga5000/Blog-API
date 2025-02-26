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

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
