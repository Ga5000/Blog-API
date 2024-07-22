package com.ga5000.blog.api.dtos;



import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDTO(UUID id,
                         String text,
                         LocalDateTime createdDate,
                         UserDTO user) {
}
