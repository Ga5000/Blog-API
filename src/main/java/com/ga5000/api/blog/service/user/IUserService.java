package com.ga5000.api.blog.service.user;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.user.UserInfoResponse;
import com.ga5000.api.blog.middleware.exception.entity.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface IUserService {
    Page<PostSearchResponse> getUserPosts(String username, Pageable pageable, Sort.Direction direction);
    UserInfoResponse getUserInfo(String username) throws EntityNotFoundException;
    UserInfoResponse getCurrentUserInfo();
    User getUserById(UUID userId) throws EntityNotFoundException;

}
