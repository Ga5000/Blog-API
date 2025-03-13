package com.ga5000.api.blog.repository.post;

import com.ga5000.api.blog.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {
    @Query("SELECT p FROM Post p ORDER BY FUNCTION('RAND')")
    Page<Post> findRandomPosts(Pageable pageable);
}
