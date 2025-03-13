package com.ga5000.api.blog.controller.auth;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IAuthController {
    ResponseEntity<Map<String,String>> getToken();
}
