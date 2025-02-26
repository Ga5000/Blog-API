package com.ga5000.api.blog.domain.user;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.comment.CommentEngagement;
import com.ga5000.api.blog.domain.engagement.post.PostEngagement;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.role.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostEngagement> postEngagements; // likes and dislikes the user made on a post

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEngagement> engagements; // likes and dislikes the suer made on a comment
}
