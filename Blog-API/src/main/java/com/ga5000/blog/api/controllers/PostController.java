package com.ga5000.blog.api.controllers;

import com.ga5000.blog.api.dtos.PostDetailsDTO;
import com.ga5000.blog.api.dtos.PostSummaryDTO;
import com.ga5000.blog.api.models.PostModel;
import com.ga5000.blog.api.models.UserModel;
import com.ga5000.blog.api.services.PostService;
import com.ga5000.blog.api.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@RequestParam("title") String title,
                                             @RequestParam("image") MultipartFile image,
                                             @RequestParam("content") String content) throws IOException {
        var post = new PostModel();
        post.setTitle(title);
        post.setImage(image.getBytes());
        post.setContent(content);
        post.setCreationDate(LocalDateTime.now());

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        UserModel user = (UserModel) userService.findByUsername(username);
        post.setUser(user);

        postService.savePost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }



    @GetMapping("/post-details/{id}")
    public ResponseEntity<Object> getPostInfo(@PathVariable UUID id) {
        Optional<PostDetailsDTO> postOptional = postService.findPostDetailsById(id);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(postOptional.get());
    }

    @GetMapping("/summary")
    public ResponseEntity<List<PostSummaryDTO>> getPostsSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAllPostsSummary());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable UUID id,
                                             @RequestParam("title") String title,
                                             @RequestParam("image") MultipartFile image,
                                             @RequestParam("content") String content) throws IOException {
        Optional<PostModel> postToUpdate = postService.findPostById(id);
        if (postToUpdate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        var post = postToUpdate.get();
        post.setTitle(title);
        post.setImage(image.getBytes());
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        UserModel user = (UserModel) userService.findByUsername(username);
        post.setUser(user);

        return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id) {
        Optional<PostModel> postToDelete = postService.findPostById(id);
        if (postToDelete.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }
}

