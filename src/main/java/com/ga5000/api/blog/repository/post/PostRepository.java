package com.ga5000.api.blog.repository.post;

import com.ga5000.api.blog.domain.post.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

        @NotNull
        @Override
        @EntityGraph(attributePaths = {"image", "author"})
        Page<Post> findAll(Specification<Post> spec, @NotNull Pageable pageable);

        @EntityGraph(attributePaths = {"image", "author"})
        Page<Post> findPostsByAuthor_Username(String username, Pageable pageable);




}
