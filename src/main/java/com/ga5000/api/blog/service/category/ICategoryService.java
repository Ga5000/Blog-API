package com.ga5000.api.blog.service.category;

import com.ga5000.api.blog.dto.CategoryRequest;
import com.ga5000.api.blog.dto.CategoryResponse;
import com.ga5000.api.blog.exception.other.NoUpdateNeededException;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    void createCategory(CategoryRequest categoryRequest);
    void createCategories(List<CategoryRequest> categoryRequests);

    void updateCategory(UUID categoryId, CategoryRequest categoryRequest) throws NoUpdateNeededException;
    void deleteCategory(UUID categoryId);

    List<CategoryResponse> getCategories();
}
