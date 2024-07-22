package com.ga5000.blog.api.services;

import com.ga5000.blog.api.models.CommentModel;
import com.ga5000.blog.api.repositories.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentModel saveComment(CommentModel comment){
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID id){
        commentRepository.deleteById(id);
    }

    public Optional<CommentModel> findCommentById(UUID id){ return commentRepository.findById(id);}
}
