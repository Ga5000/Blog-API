package com.ga5000.api.blog.exception.category;

import com.ga5000.api.blog.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException() {
        super("Category not found", HttpStatus.NOT_FOUND);
    }
}
