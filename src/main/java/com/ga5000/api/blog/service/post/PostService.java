package com.ga5000.api.blog.service.post;

import com.ga5000.api.blog.domain.image.Image;
import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.domain.user.role.Role;
import com.ga5000.api.blog.dto.comment.CommentRequest;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.image.ImageRequest;
import com.ga5000.api.blog.dto.post.PostRequest;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import com.ga5000.api.blog.middleware.exception.auth.UnauthorizedOperationException;
import com.ga5000.api.blog.middleware.exception.entity.EntityNotFoundException;
import com.ga5000.api.blog.repository.post.PostRepository;
import com.ga5000.api.blog.repository.post.specification.PostSpecification;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.category.CategoryService;
import com.ga5000.api.blog.service.comment.CommentService;
import com.ga5000.api.blog.service.engagement.EngagementService;
import com.ga5000.api.blog.service.image.ImageService;
import com.ga5000.api.blog.service.user.UserService;
import com.ga5000.api.blog.utils.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;
import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

@Service
public class PostService implements IPostService{
    private final String POST_NOT_FOUND_MESSAGE = "Post not found";
    private final PostRepository postRepository;
    private final DtoMapper dtoMapper;
    private final AuthService authService;
    private final UserService userService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final EngagementService engagementService;

    public PostService(PostRepository postRepository, DtoMapper dtoMapper, AuthService authService, UserService userService,
                       ImageService imageService, CategoryService categoryService, CommentService commentService,
                       EngagementService engagementService) {
        this.postRepository = postRepository;
        this.dtoMapper = dtoMapper;
        this.authService = authService;
        this.userService = userService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.engagementService = engagementService;
    }

    @Transactional
    @Override
    public void createPost(PostRequest request, MultipartFile file) {
        User author = userService.getUserById(authService.getCurrentUserId());
        var newPost = new Post(request.title(), request.content(), author);
        savePost(newPost);
        imageService.uploadImage(new ImageRequest(file), newPost);
        categoryService.associateCategoriesWithPost(request.categories(), newPost.getPostId());
    }

    @Transactional
    @Override
    public void deletePost(UUID postId) {
        var existingPost = findPostById(postId);
        isOwnerOrAdmin(userService.getUserById(authService.getCurrentUserId()),existingPost);
        postRepository.deleteById(postId);
    }

    @Transactional
    @Override
    public void updatePostTitle(UUID postId, String newTitle) {
        var existingPost = findPostById(postId);
        isOwnerOrAdmin(userService.getUserById(authService.getCurrentUserId()),existingPost);
        existingPost.setTitle(newTitle);
        existingPost.setUpdatedAt(LocalDateTime.now());
        savePost(existingPost);
    }

    @Override
    public void updatePostContent(UUID postId, String newContent) {
        var existingPost = findPostById(postId);
        isOwnerOrAdmin(userService.getUserById(authService.getCurrentUserId()),existingPost);
        existingPost.setContent(newContent);
        existingPost.setUpdatedAt(LocalDateTime.now());
        savePost(existingPost);
    }

    @Transactional
    @Override
    public void updatePostImage(UUID postId, MultipartFile newFile) {
        var existingPost = findPostById(postId);
        isOwnerOrAdmin(userService.getUserById(authService.getCurrentUserId()),existingPost);
        existingPost.setUpdatedAt(LocalDateTime.now());
        if (existingPost.getImage() != null) {
            Image oldImage = existingPost.getImage();
            oldImage.setPost(null);
            existingPost.setImage(null);
            savePost(existingPost);
            imageService.deleteImage(oldImage.getImageId());
        }
        // Upload new image and assign it
        imageService.uploadImage(new ImageRequest(newFile), existingPost);
        savePost(existingPost);
    }

    @Transactional
    @Override
    public void likeOrDislikePost(UUID postId, boolean likeOrDislike) {
        var existingPost = findPostById(postId);
        engagementService.likeOrDislikePost(likeOrDislike, existingPost);

    }

    @Transactional
    @Override
    public void commentOnPost(UUID postId, CommentRequest request) {
        var existingPost = findPostById(postId);
        commentService.createComment(existingPost, request);
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponse getPost(UUID postId) {
        var post = findPostById(postId);

        return dtoMapper.toPostResponse(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostSearchResponse> searchPosts(SearchPostParams params, Pageable pageable, Sort.Direction direction) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(direction, "title"));


        Specification<Post> spec = PostSpecification.filterByParams(params);
        Page<Post> postPage = postRepository.findAll(spec, pageable);


        return postPage.map(dtoMapper::toPostSearchResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getPostComments(UUID postId, Pageable pageable, Sort.Direction direction) {
        return commentService.getPostComments(postId, pageable, direction);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Post> getUserPosts(String username, Pageable pageable) {
        return postRepository.findPostsByAuthor_Username(username, pageable);
    }

    private Post findPostById(UUID postId) throws EntityNotFoundException{
        return findByIdOrThrow(postId, postRepository, () -> new EntityNotFoundException(POST_NOT_FOUND_MESSAGE));
    }

    private void savePost(Post post) {
        postRepository.save(post);
    }

    private void isOwnerOrAdmin(User user, Post post) throws UnauthorizedOperationException {
        if(!post.getAuthor().equals(user) || !user.getRole().equals(Role.ADMIN)){
            throw new UnauthorizedOperationException("You are not authorized to edit or delete this post");
        }
    }
}
