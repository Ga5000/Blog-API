package com.ga5000.api.blog.exception.token;

import com.ga5000.api.blog.exception.BaseException;
import org.springframework.http.HttpStatus;

public class TokenValidationException extends BaseException {
    public TokenValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
