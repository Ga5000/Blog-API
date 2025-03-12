package com.ga5000.api.blog.service.post;

import com.ga5000.api.blog.dto.post.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import com.ga5000.api.blog.repository.post.PostRepository;
import com.ga5000.api.blog.utils.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

import static com.ga5000.api.blog.utils.RepositoryUtils.findByIdOrThrow;

public class PostService implements IPostService{
    private final String POST_NOT_FOUND_MESSAGE = "Post not found";
    private final PostRepository postRepository;
    private final DtoMapper dtoMapper;

    public PostService(PostRepository postRepository, DtoMapper dtoMapper) {
        this.postRepository = postRepository;
        this.dtoMapper = dtoMapper;
    }


    @Override
    public void deletePost(UUID postId) {

    }

    @Override
    public Page<PostSearchResponse> searchPosts(SearchPostParams params, Pageable pageable, Sort.Direction direction) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return null;
    }
}
