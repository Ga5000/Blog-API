package com.ga5000.api.blog.repository.comment;

import com.ga5000.api.blog.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {


    Page<Comment> findByParentComment_CommentId(UUID commentId, Pageable pageable);

    Page<Comment> findCommentsByPost_PostId(UUID postId, Pageable pageable);
}
