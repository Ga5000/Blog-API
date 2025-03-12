package com.ga5000.api.blog.controller.auth;
import com.ga5000.api.blog.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String,String>> getToken() {
        String token = authService.getToken();

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
    }

}