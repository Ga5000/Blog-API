package com.ga5000.api.blog.service.category;

import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.dto.category.CategoryRequest;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import com.ga5000.api.blog.dto.category.UpdateCategoryRequest;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ICategoryService {
    void createCategory(CategoryRequest request);
    void createCategories(List<CategoryRequest> requests);
    void updateCategory(UpdateCategoryRequest request);
    void deleteCategory(UUID categoryId);

    Set<CategoryResponse> getCategories();

    void associateCategoriesWithPost(List<UUID> categoryIds, UUID postId);


}
