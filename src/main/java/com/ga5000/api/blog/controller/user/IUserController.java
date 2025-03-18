package com.ga5000.api.blog.controller.user;

import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.user.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    ResponseEntity<PagedModel<EntityModel<PostSearchResponse>>> getUserPosts(String username, Pageable pageable, Sort.Direction sortDirection);
    ResponseEntity<UserInfoResponse> getUserInfo(String username);
    ResponseEntity<UserInfoResponse> getCurrentUserInfo();
}
