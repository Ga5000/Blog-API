package com.ga5000.api.blog.service.post;


import com.ga5000.api.blog.dto.post.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface IPostService {

    void deletePost(UUID postId);
    Page<PostSearchResponse> searchPosts(SearchPostParams params, Pageable pageable, Sort.Direction direction);
}
