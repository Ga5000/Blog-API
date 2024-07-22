package com.ga5000.blog.api.controllers;

import com.ga5000.blog.api.dtos.CommentRequestDTO;
import com.ga5000.blog.api.models.CommentModel;
import com.ga5000.blog.api.models.PostModel;
import com.ga5000.blog.api.models.UserModel;
import com.ga5000.blog.api.services.CommentService;
import com.ga5000.blog.api.services.PostService;
import com.ga5000.blog.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create/{postId}/{userId}")
    public ResponseEntity<Object> createComment(@PathVariable UUID postId, @PathVariable UUID userId, @RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        Optional<PostModel> post = postService.findPostById(postId);
        Optional<UserModel> user = userService.findUserById(userId);

        if (post.isEmpty() || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong");
        }

        var comment = new CommentModel();
        BeanUtils.copyProperties(commentRequestDTO, comment);
        comment.setCreationDate(LocalDateTime.now());
        comment.setPost(post.get());
        comment.setUser(user.get());
        commentService.saveComment(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
    }

    @PutMapping("/update/{id}/{postId}/{userId}")
    public ResponseEntity<Object> updateComment(@PathVariable UUID id, @PathVariable UUID postId, @PathVariable UUID userId, @RequestBody @Valid CommentRequestDTO commentRequestDTO) {
        Optional<CommentModel> commentToUpdate = commentService.findCommentById(id);
        Optional<PostModel> post = postService.findPostById(postId);
        Optional<UserModel> user = userService.findUserById(userId);

        if (commentToUpdate.isEmpty() || post.isEmpty() || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong");
        }

        var comment = commentToUpdate.get();
        BeanUtils.copyProperties(commentRequestDTO, comment);
        comment.setPost(post.get());
        comment.setUser(user.get());
        commentService.saveComment(comment);

        return ResponseEntity.status(HttpStatus.OK).body("Comment updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable UUID id) {
        Optional<CommentModel> commentToDelete = commentService.findCommentById(id);
        if (commentToDelete.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).body("Comment deleted successfully");
    }
}
