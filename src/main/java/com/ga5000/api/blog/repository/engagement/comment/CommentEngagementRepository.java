package com.ga5000.api.blog.repository.engagement.comment;

import com.ga5000.api.blog.domain.engagement.comment.CommentEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentEngagementRepository extends JpaRepository<CommentEngagement, UUID> {
}
