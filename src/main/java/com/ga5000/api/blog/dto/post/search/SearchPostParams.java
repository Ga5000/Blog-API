package com.ga5000.api.blog.dto.post.search;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record SearchPostParams(String title, Set<UUID> categories, LocalDate minDate, LocalDate maxDate, Boolean mostLiked) {
}
