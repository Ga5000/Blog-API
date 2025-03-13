package com.ga5000.api.blog.controller.post;

import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.post.PostRequest;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import org.springframework.data.domain.Page;
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
    ResponseEntity<Map<String, String>> createPost(PostRequest request);
    ResponseEntity<Map<String, String>> updatePostTitle(UUID postId, String newTitle);
    ResponseEntity<Map<String, String>> updatePostContent(UUID postId, String newContent);
    ResponseEntity<Map<String, String>> updatePostImage(UUID postId, MultipartFile file);
    ResponseEntity<Map<String, String>> deletePost(UUID postId);
    ResponseEntity<PostResponse> getPostInfo(UUID postId);
    ResponseEntity<Page<CommentResponse>> getPostComments(UUID postId, int pageSize);

    ResponseEntity<PagedModel<EntityModel<PostSearchResponse>>>
    searchPosts(String title, Set<UUID> categories, LocalDate minDate, LocalDate maxDate,
                                Boolean mostLiked, Pageable pageable, Sort.Direction direction);
}
