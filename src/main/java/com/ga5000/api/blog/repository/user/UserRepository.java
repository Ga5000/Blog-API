package com.ga5000.api.blog.repository.user;

import com.ga5000.api.blog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByGoogleId(String googleId);

    Optional<User> findByEmail(String email);
}
