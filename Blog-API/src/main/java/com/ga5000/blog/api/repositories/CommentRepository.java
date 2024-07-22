package com.ga5000.blog.api.repositories;

import com.ga5000.blog.api.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, UUID> {
}
