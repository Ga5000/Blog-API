package com.ga5000.api.blog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_KEY = "error";

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, String>> handleBaseException(BaseException ex) {
        return ResponseEntity.status(ex.getStatus()).body(Map.of(ERROR_KEY, ex.getMessage()));
    }
}
