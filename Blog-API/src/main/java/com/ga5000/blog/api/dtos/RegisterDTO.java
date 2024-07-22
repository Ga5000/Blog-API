package com.ga5000.blog.api.dtos;

import com.ga5000.blog.api.security.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
