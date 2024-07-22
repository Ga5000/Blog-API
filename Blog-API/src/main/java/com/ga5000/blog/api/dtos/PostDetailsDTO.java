package com.ga5000.blog.api.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PostDetailsDTO(UUID id,
                             String title,
                             byte[] image,
                             String content,
                             LocalDateTime creationDate,
                             LocalDateTime updatedAt,
                             UserDTO user,
                             List<CommentDTO> comments) {
}
