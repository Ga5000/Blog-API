package com.ga5000.api.blog.dto.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCategoryRequest(@NotNull(message = "Category id is required") UUID categoryId,
                                    @NotEmpty(message = "Category name is required") String name) {
}
