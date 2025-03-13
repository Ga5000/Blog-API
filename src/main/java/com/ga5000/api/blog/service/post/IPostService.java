package com.ga5000.api.blog.service.post;


import com.ga5000.api.blog.dto.comment.CommentResponse;
import com.ga5000.api.blog.dto.post.PostRequest;
import com.ga5000.api.blog.dto.post.PostResponse;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IPostService {

    void createPost(PostRequest request);
    void deletePost(UUID postId);
    void updatePostTitle(UUID postId, String newTitle);
    void updatePostContent(UUID postId, String newContent);
    void updatePostImage(UUID postId, MultipartFile newFile);
    PostResponse getPost(UUID postId);
    Page<PostSearchResponse> searchPosts(SearchPostParams params, Pageable pageable, Sort.Direction direction);
    Page<CommentResponse> getPostComments(UUID postId, int pageSize);
}
