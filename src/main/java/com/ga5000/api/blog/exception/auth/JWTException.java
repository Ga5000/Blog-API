package com.ga5000.api.blog.exception.auth;

import com.ga5000.api.blog.exception.BaseException;
import org.springframework.http.HttpStatus;

public class JWTException extends BaseException {
    public JWTException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
