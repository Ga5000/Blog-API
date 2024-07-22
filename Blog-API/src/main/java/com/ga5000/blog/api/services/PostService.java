package com.ga5000.blog.api.services;

import com.ga5000.blog.api.dtos.CommentDTO;
import com.ga5000.blog.api.dtos.PostDetailsDTO;
import com.ga5000.blog.api.dtos.PostSummaryDTO;
import com.ga5000.blog.api.dtos.UserDTO;
import com.ga5000.blog.api.models.PostModel;
import com.ga5000.blog.api.repositories.PostRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional
    public PostModel savePost(PostModel post) {
        return postRepository.save(post);
    }


    public List<PostSummaryDTO> findAllPostsSummary() {
        return postRepository.findAllPostsSummary();
    }

    @Transactional
    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }

    public Optional<PostDetailsDTO> findPostDetailsById(UUID id) {
        Optional<PostModel> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            return Optional.empty();
        }

        PostModel post = postOptional.get();
        UserDTO userDTO = new UserDTO(post.getUser().getId(), post.getUser().getUsername());
        List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getContent(), comment.getCreationDate(),
                        new UserDTO(comment.getUser().getId(), comment.getUser().getUsername())))
                .collect(Collectors.toList());

        PostDetailsDTO dto = new PostDetailsDTO(
                post.getId(),
                post.getTitle(),
                post.getImage(),
                post.getContent(),
                post.getCreationDate(),
                post.getUpdatedAt(),
                userDTO,
                commentDTOs
        );

        return Optional.of(dto);
    }

    public Optional<PostModel> findPostById(UUID id) {
        return postRepository.findById(id);
    }
}


