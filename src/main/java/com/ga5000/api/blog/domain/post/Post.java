package com.ga5000.api.blog.domain.post;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.domain.comment.Comment;
import com.ga5000.api.blog.domain.engagement.post.PostEngagement;
import com.ga5000.api.blog.domain.image.Image;
import com.ga5000.api.blog.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID postId;

    private String title;

    private String content; // in html format

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToOne(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostEngagement> postEngagements = new ArrayList<>();

    public Post() {}

    public Post(String title, String content,LocalDate updatedAt, Image image, User author,
                Set<Category> categories, List<Comment> comments, List<PostEngagement> postEngagements) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDate.now();
        this.updatedAt = updatedAt;
        this.image = image;
        this.author = author;
        this.categories = categories;
        this.comments = comments;
        this.postEngagements = postEngagements;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<PostEngagement> getPostEngagements() {
        return postEngagements;
    }

    public void setPostEngagements(List<PostEngagement> postEngagements) {
        this.postEngagements = postEngagements;
    }
}
