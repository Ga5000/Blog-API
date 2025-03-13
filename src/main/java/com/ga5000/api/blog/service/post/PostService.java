package com.ga5000.api.blog.service.post;

import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.image.ImageRequest;
import com.ga5000.api.blog.dto.post.PostRequest;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import com.ga5000.api.blog.repository.post.PostRepository;
import com.ga5000.api.blog.repository.post.specification.PostSpecification;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.category.CategoryService;
import com.ga5000.api.blog.service.comment.CommentService;
import com.ga5000.api.blog.service.image.ImageService;
import com.ga5000.api.blog.utils.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RepositoryUtils.existsByIdOrThrow;
import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

@Service
public class PostService implements IPostService{
    private final String POST_NOT_FOUND_MESSAGE = "Post not found";
    private final PostRepository postRepository;
    private final DtoMapper dtoMapper;
    private final AuthService authService;
    private final ImageService imageService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    public PostService(PostRepository postRepository, DtoMapper dtoMapper, AuthService authService,
                       ImageService imageService, CategoryService categoryService, CommentService commentService) {
        this.postRepository = postRepository;
        this.dtoMapper = dtoMapper;
        this.authService = authService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }


    @Transactional
    @Override
    public void createPost(PostRequest request) {
        User author = authService.getCurrentUser();
        var newPost = new Post(request.title(), request.content(), author);
        savePost(newPost);
        imageService.uploadImage(new ImageRequest(request.file()), newPost);
        categoryService.associateCategoriesWithPost(request.categories(), newPost.getPostId());
    }

    @Transactional
    @Override
    public void deletePost(UUID postId) {
       existsByIdOrThrow(postId, postRepository, () -> new EntityNotFoundException(POST_NOT_FOUND_MESSAGE));
        postRepository.deleteById(postId);
    }

    @Transactional
    @Override
    public void updatePostTitle(UUID postId, String newTitle) {
        var existingPost = findPostById(postId);
        existingPost.setTitle(newTitle);
        savePost(existingPost);
    }

    @Override
    public void updatePostContent(UUID postId, String newContent) {
        var existingPost = findPostById(postId);
        existingPost.setContent(newContent);
        savePost(existingPost);
    }

    @Transactional
    @Override
    public void updatePostImage(UUID postId, MultipartFile newFile) {
        var existingPost = findPostById(postId);
        imageService.deleteImage(existingPost.getImage().getImageId());
        imageService.uploadImage(new ImageRequest(newFile), existingPost);
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

        Page<Post> postPage;
        if (hasSearchParams(params)){
            Specification<Post> spec = PostSpecification.filterByParams(params);
            postPage = postRepository.findAll(spec, pageable);
        }else{
            postPage = postRepository.findRandomPosts(pageable); // default post page
        }

        return postPage.map(dtoMapper::toPostSearchResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommentResponse> getPostComments(UUID postId, int pageSize) {
        var post = findPostById(postId);

        return commentService.getPostComments(post, pageSize);
    }

    private Post findPostById(UUID postId) throws EntityNotFoundException{
        return findByIdOrThrow(postId, postRepository, () -> new EntityNotFoundException(POST_NOT_FOUND_MESSAGE));
    }


    private void savePost(Post post) {
        postRepository.save(post);
    }

    private boolean hasSearchParams(SearchPostParams params) {
        return (params.title() != null && !params.title().isEmpty()) ||
                (params.categories() != null && !params.categories().isEmpty()) ||
                (params.minDate() != null) ||
                (params.maxDate() != null) ||
                (params.mostLiked() != null);
    }


}
