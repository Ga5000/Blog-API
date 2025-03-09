package com.ga5000.api.blog.repository.category;

import com.ga5000.api.blog.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByNameIgnoreCase(String name);

    @Query(value = "SELECT c FROM categories c " +
            "INNER JOIN post_category pc ON c.category_id = pc.category_id" +
            " WHERE pc.post_id = :post_id ", nativeQuery = true)
    Set<Category> findPostCategories(UUID post_id);


}
