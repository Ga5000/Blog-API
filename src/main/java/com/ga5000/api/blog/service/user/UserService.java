package com.ga5000.api.blog.service.user;

import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.user.UserInfoResponse;
import com.ga5000.api.blog.middleware.exception.entity.EntityNotFoundException;
import com.ga5000.api.blog.repository.user.UserRepository;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.post.PostService;
import com.ga5000.api.blog.utils.DtoMapper;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PostService postService;
    private final AuthService authService;
    private final DtoMapper dtoMapper;
    private final EntityManager entityManager;


    public UserService(UserRepository userRepository,
                       @Lazy PostService postService, AuthService authService, DtoMapper dtoMapper, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.authService = authService;
        this.dtoMapper = dtoMapper;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostSearchResponse> getUserPosts(String username, Pageable pageable, Sort.Direction direction) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(direction, "createdAt"));

        Page<Post> posts = postService.getUserPosts(username, pageable);

        return posts.map(dtoMapper::toPostSearchResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponse getUserInfo(String username) throws EntityNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username));

        return dtoMapper.toUserInfoResponse(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponse getCurrentUserInfo() {
        var currentUser = getUserById(authService.getCurrentUserId());

        return dtoMapper.toUserInfoResponse(currentUser);
    }

    @Override
    public User getUserById(UUID userId) throws EntityNotFoundException{
        return entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .setHint("org.hibernate.cacheable", true)
                .getSingleResult();
    }
}
