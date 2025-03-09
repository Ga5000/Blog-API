package com.ga5000.api.blog.dto.category;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(@NotEmpty(message = "Category name is required") String name) {
}
