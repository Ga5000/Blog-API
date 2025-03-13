package com.ga5000.api.blog.controller.category;

import com.ga5000.api.blog.dto.category.CategoryRequest;
import com.ga5000.api.blog.dto.category.CategoryResponse;
import com.ga5000.api.blog.dto.category.UpdateCategoryRequest;
import com.ga5000.api.blog.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.ga5000.api.blog.utils.RequestMessageUtils.SUCCESS_KEY;

@RestController
@RequestMapping("/api/categories")
public class CategoryController implements ICategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Map<String, String>> createCategory(@Valid @RequestBody CategoryRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(SUCCESS_KEY, "Category created successfully"));
    }

    @PostMapping("/bulk")
    @Override
    public ResponseEntity<Map<String, String>> createCategories(@Valid @RequestBody List<CategoryRequest> requests) {
        categoryService.createCategories(requests);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(SUCCESS_KEY, "Categories were created successfully"));
    }

    @PatchMapping("/{categoryId}")
    @Override
    public ResponseEntity<Map<String, String>> updateCategory(@PathVariable UUID categoryId,
                                                              @Valid @RequestBody CategoryRequest request) {
        categoryService.updateCategory(new UpdateCategoryRequest(categoryId, request.name()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of(SUCCESS_KEY, "Category updated successfully"));
    }

    @DeleteMapping("/{categoryId}")
    @Override
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(Map.of(SUCCESS_KEY, "Category deleted successfully"));
    }

    @GetMapping
    @Override
    public ResponseEntity<Set<CategoryResponse>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getCategories());
    }
}
