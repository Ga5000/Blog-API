package com.ga5000.api.blog.repository.engagement.post;

import com.ga5000.api.blog.domain.engagement.post.PostEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostEngagementRepository extends JpaRepository<PostEngagement, UUID> {
    @Query(value = "SELECT * FROM post_engagements_tb WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    Optional<PostEngagement> findByUserIdAndPostId(@Param("userId") UUID userId, @Param("postId") UUID postId);
}
