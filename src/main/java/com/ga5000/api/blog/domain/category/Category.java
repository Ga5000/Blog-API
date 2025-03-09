package com.ga5000.api.blog.domain.category;

import com.ga5000.api.blog.domain.post.Post;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts = new HashSet<>();

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Set<Post> posts) {
        this.name = name;
        this.posts = posts;
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

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
