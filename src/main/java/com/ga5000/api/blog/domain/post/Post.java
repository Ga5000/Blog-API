package com.ga5000.api.blog.domain.post;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.post.PostEngagement;
import com.ga5000.api.blog.domain.image.Image;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID postId;

    private String title;

    private String description;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToMany
    @JoinTable(
            name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User author;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostEngagement> postEngagements = new ArrayList<>(); // likes and dislikes on the post




}
