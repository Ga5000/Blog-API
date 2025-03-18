package com.ga5000.api.blog.repository.engagement.comment;

import com.ga5000.api.blog.domain.engagement.comment.CommentEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentEngagementRepository extends JpaRepository<CommentEngagement, UUID> {
    @Query(value = "SELECT * FROM comment_engagements_tb WHERE user_id = :userId AND comment_id = :commentId", nativeQuery = true)
    Optional<CommentEngagement> findByUserIdAndCommentId(@Param("userId") UUID userId, @Param("commentId") UUID commentId);
}
