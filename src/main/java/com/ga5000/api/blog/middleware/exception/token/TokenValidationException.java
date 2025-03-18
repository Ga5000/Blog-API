package com.ga5000.api.blog.middleware.exception.token;

import com.ga5000.api.blog.middleware.exception.BaseException;
import org.springframework.http.HttpStatus;

public class TokenValidationException extends BaseException {
    public TokenValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
