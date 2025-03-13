package com.ga5000.api.blog.controller.post;

import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.post.PostRequest;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import com.ga5000.api.blog.service.post.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RequestMessageUtils.SUCCESS_KEY;

@RestController
@RequestMapping("/api/posts")
public class PostController implements IPostController{
    private final PostService postService;
    private final PagedResourcesAssembler<PostSearchResponse> pagedResourcesAssembler;

    public PostController(PostService postService, PagedResourcesAssembler<PostSearchResponse> pagedResourcesAssembler) {
        this.postService = postService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<Map<String, String>> createPost(@ModelAttribute PostRequest request) {
        postService.createPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(SUCCESS_KEY, "Post created successfully"));
    }

    @PatchMapping("/{postId}/title")
    @Override
    public ResponseEntity<Map<String, String>> updatePostTitle(@PathVariable("postId") UUID postId, @RequestParam("newTitle") String newTitle) {
       postService.updatePostTitle(postId, newTitle);
       return ResponseEntity.status(HttpStatus.OK).body(Map.of(SUCCESS_KEY, "Post title updated successfully"));
    }

    @PatchMapping("/{postId}/content")
    @Override
    public ResponseEntity<Map<String, String>> updatePostContent(@PathVariable("postId") UUID postId, @RequestBody String newContent) {
        postService.updatePostContent(postId, newContent);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(SUCCESS_KEY, "Post content updated successfully"));
    }


    @PatchMapping(value = "/{postId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<Map<String, String>> updatePostImage(@PathVariable("postId") UUID postId, @ModelAttribute MultipartFile file) {
        postService.updatePostImage(postId, file);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(SUCCESS_KEY, "Post image updated successfully"));
    }

    @DeleteMapping("/{postId}/delete")
    @Override
    public ResponseEntity<Map<String, String>> deletePost(@PathVariable("postId") UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(SUCCESS_KEY, "Post deleted successfully"));
    }

    @GetMapping("/{postId}")
    @Override
    public ResponseEntity<PostResponse> getPostInfo(@PathVariable("postId") UUID postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @GetMapping("/{postId}/comments")
    @Override
    public ResponseEntity<Page<CommentResponse>> getPostComments(@PathVariable("postId") UUID postId, int pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostComments(postId, pageSize));
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<PagedModel<EntityModel<PostSearchResponse>>> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<UUID> categories,
            @RequestParam(required = false) LocalDate minDate,
            @RequestParam(required = false) LocalDate maxDate,
            @RequestParam(required = false) Boolean mostLiked,
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        Page<PostSearchResponse> posts = postService.
                searchPosts(new SearchPostParams(title, categories, minDate, maxDate, mostLiked), pageable, direction);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedResourcesAssembler.toModel(posts, post -> EntityModel.of(post,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostInfo(post.postId()))
                                .withSelfRel())));
    }

}
