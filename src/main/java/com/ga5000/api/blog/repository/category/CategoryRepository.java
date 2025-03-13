package com.ga5000.api.blog.repository.category;

import com.ga5000.api.blog.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByNameIgnoreCase(String name);
    @Transactional
    @Modifying
    @Query(value = """
    INSERT INTO post_categories_tb (post_id, category_id)
    SELECT :postId, c.category_id FROM categories_tb c WHERE c.category_id IN (:categoriesIds)
""", nativeQuery = true)
    void associateCategoriesWithPost(@Param("postId") UUID postId, @Param("categoriesIds") List<UUID> categoriesIds);


}
