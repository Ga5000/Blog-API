package com.ga5000.blog.api.dtos;

import java.time.LocalDateTime;

public record PostSummaryDTO(String title, LocalDateTime createdAt, LocalDateTime updatedAt, String madeBy) {

}
