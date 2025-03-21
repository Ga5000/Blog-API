package com.ga5000.api.blog.middleware.exception.image;

import com.ga5000.api.blog.middleware.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UploadImageException extends BaseException {
    public UploadImageException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
