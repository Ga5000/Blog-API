package com.ga5000.api.blog.utils;

import com.ga5000.api.blog.domain.category.Category;
import com.ga5000.api.blog.dto.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public CategoryResponse toCategoryResponse(Category category){
        return new CategoryResponse(
                category.getCategoryId(),
                category.getName()
        );
    }
}
