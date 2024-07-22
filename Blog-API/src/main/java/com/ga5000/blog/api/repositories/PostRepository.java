package com.ga5000.blog.api.repositories;

import com.ga5000.blog.api.dtos.PostSummaryDTO;
import com.ga5000.blog.api.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {
    @Query("SELECT new com.ga5000.blog.api.dtos.PostSummaryDTO(p.title, p.creationDate, p.updatedAt, u.username) " +
            "FROM PostModel p JOIN p.user u")
    List<PostSummaryDTO> findAllPostsSummary();
}
