package com.ga5000.api.blog.controller.post;

import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.post.PostRequest;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.UpdatePostContentRequest;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface IPostController {
    ResponseEntity<Map<String, String>> createPost(PostRequest request, MultipartFile file);
    ResponseEntity<Map<String, String>> updatePostTitle(UUID postId, String newTitle);
    ResponseEntity<Map<String, String>> updatePostContent(UUID postId, UpdatePostContentRequest request);
    ResponseEntity<Map<String, String>> updatePostImage(UUID postId, MultipartFile file);
    ResponseEntity<Map<String, String>> deletePost(UUID postId);
    ResponseEntity<Void> likeOrDislikePost(UUID postId, boolean likeOrDislike);

    ResponseEntity<Map<String, String>> commentOnPost(UUID postId, CommentRequest request);
    ResponseEntity<PostResponse> getPostInfo(UUID postId);
    ResponseEntity<PagedModel<EntityModel<CommentResponse>>> getPostComments(UUID postId, Pageable pageable, Sort.Direction direction);

    ResponseEntity<PagedModel<EntityModel<PostSearchResponse>>>
    searchPosts(String title, Set<UUID> categories, LocalDate minDate, LocalDate maxDate,
                Pageable pageable, Sort.Direction direction);
}
