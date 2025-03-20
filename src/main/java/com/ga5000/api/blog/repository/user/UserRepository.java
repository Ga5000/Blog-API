package com.ga5000.api.blog.repository.user;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.service.user.userDetails.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT new com.ga5000.api.blog.service.user.userDetails.CustomUserDetails(" +
            "u.userId, u.username, u.role) " +
            "FROM User u WHERE u.userId = :userId")
    CustomUserDetails findCustomUserDetailsByUserId(@Param("userId") UUID userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
