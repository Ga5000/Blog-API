package com.ga5000.api.blog.dto.user;

import java.time.LocalDate;
public record UserInfoResponse(String username,String profilePicture, LocalDate createdAt) {
}
