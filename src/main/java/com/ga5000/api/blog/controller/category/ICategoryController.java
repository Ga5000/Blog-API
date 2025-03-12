package com.ga5000.api.blog.controller.category;

import com.ga5000.api.blog.dto.category.CategoryRequest;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public interface ICategoryController {
    ResponseEntity<Map<String, String>> createCategory(@Valid @RequestBody CategoryRequest request);
    ResponseEntity<Map<String, String>> createCategories(@Valid @RequestBody List<CategoryRequest> requests);
    ResponseEntity<Map<String, String>> updateCategory(@PathVariable UUID categoryId, @Valid @RequestBody CategoryRequest request);
    ResponseEntity<Map<String, String>> deleteCategory(@PathVariable UUID categoryId);

    ResponseEntity<Set<CategoryResponse>> getCategories();
}
