package com.ga5000.api.blog.domain.user;

import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.role.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users_tb")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    private String username; // username -> provided by google Oauth

    @Column(nullable = false, unique = true)
    private String email; // e-mail -> provided by google Oauth

    private String profilePicture; // url -> provided by google Oauth

    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "author", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "author", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public User() {}

    public User(String username, String email, String profilePicture,
                List<Post> posts, List<Comment> comments) {
        this.username = username;
        this.email = email;
        this.profilePicture = profilePicture;
        this.createdAt = LocalDate.now();
        this.role = Role.USER;
        this.posts = posts;
        this.comments = comments;
    }

    public User(String email, String username, String profilePicture) {
        this.username = username;
        this.email = email;
        this.profilePicture = profilePicture;
        this.createdAt = LocalDate.now();
        this.role = Role.USER;
    }

    public User(String email, String username, String profilePicture, Role role) {
        this.username = username;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role;
    }


    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
