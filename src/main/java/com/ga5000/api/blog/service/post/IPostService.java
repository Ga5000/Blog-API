package com.ga5000.api.blog.service.post;


import com.ga5000.api.blog.dto.post.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService {
    Page<PostSearchResponse> searchPosts(SearchPostParams params, Pageable pageable);
}
