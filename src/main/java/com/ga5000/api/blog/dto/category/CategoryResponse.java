package com.ga5000.api.blog.dto.category;

import java.io.Serializable;
import java.util.UUID;

public record CategoryResponse(UUID categoryId, String name) implements Serializable {
}
