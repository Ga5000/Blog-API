package com.ga5000.api.blog.middleware.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.data.redis.RedisConnectionFailureException;

import io.minio.errors.MinioException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_KEY = "error";

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, String>> handleBaseException(BaseException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(Map.of(ERROR_KEY, ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        String message = "An unexpected error occurred. Please try again later.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        String message = "A file is missing from the request.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseEntity<Map<String, String>> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        String message = "Our data service is currently unavailable. Please try again later.";
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler(MinioException.class)
    public ResponseEntity<Map<String, String>> handleMinioException(MinioException ex) {
        String message = "File storage service is currently unavailable. Please try again later.";
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String message = "The requested endpoint was not found.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ERROR_KEY, errors));
    }
}
