package com.ga5000.api.blog.middleware.exception.auth;

import com.ga5000.api.blog.middleware.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedOperationException extends BaseException {
    public UnauthorizedOperationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
