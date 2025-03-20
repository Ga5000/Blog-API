package com.ga5000.api.blog.middleware.exception.entity;

import com.ga5000.api.blog.middleware.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends BaseException {
    public EntityAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
