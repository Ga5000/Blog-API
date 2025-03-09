package com.ga5000.api.blog.repository.engagement.post;

import com.ga5000.api.blog.domain.engagement.post.PostEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostEngagementRepository extends JpaRepository<PostEngagement, UUID> {
}
