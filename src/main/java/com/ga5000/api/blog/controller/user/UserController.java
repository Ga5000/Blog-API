package com.ga5000.api.blog.controller.user;

import com.ga5000.api.blog.controller.post.PostController;
import com.ga5000.api.blog.dto.post.search.PostSearchResponse;
import com.ga5000.api.blog.dto.user.UserInfoResponse;
import com.ga5000.api.blog.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController implements IUserController {
    private final UserService userService;
    private final PagedResourcesAssembler<PostSearchResponse> pagedPostsResourcesAssembler;

    public UserController(UserService userService, PagedResourcesAssembler<PostSearchResponse> pagedPostsResourcesAssembler) {
        this.userService = userService;
        this.pagedPostsResourcesAssembler = pagedPostsResourcesAssembler;
    }

    @GetMapping("/{username}/posts")
    @Override
    public ResponseEntity<PagedModel<EntityModel<PostSearchResponse>>> getUserPosts(@PathVariable("username") String username,
                                                                                    @PageableDefault(size = 5) Pageable pageable,
                                                                                    @RequestParam(defaultValue = "ASC")
                                                                                        Sort.Direction direction) {

        Page<PostSearchResponse> posts = userService.getUserPosts(username, pageable, direction);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pagedPostsResourcesAssembler.toModel(posts, post -> EntityModel.of(post,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostInfo(post.postId()))
                                .withSelfRel())));
    }

    @GetMapping("/{username}")
    @Override
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable("username")String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(username));
    }

    @GetMapping("/me")
    @Override
    public ResponseEntity<UserInfoResponse> getCurrentUserInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUserInfo());
    }
}
