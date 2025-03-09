package com.ga5000.api.blog.exception.entity;

import com.ga5000.api.blog.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends BaseException {
    public EntityAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
