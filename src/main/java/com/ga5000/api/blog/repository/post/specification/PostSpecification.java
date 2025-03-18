package com.ga5000.api.blog.repository.post.specification;

import com.ga5000.api.blog.domain.post.Post;
import com.ga5000.api.blog.dto.post.search.SearchPostParams;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<Post> filterByParams(SearchPostParams params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (params.title() != null && !params.title().isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("title")), "%" + params.title().toLowerCase() + "%"));
            }
            if (params.categories() != null && !params.categories().isEmpty()) {
                predicates.add(root.join("categories").get("id").in(params.categories()));
            }
            if (params.minDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), params.minDate()));
            }
            if (params.maxDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), params.maxDate()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
