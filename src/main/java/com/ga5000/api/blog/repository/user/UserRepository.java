package com.ga5000.api.blog.repository.user;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.domain.user.customUserDetails.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT u.email, u.password, u.role FROM users u WHERE u.email = :email", nativeQuery = true)
    CustomUserDetails findCustomUserDetailsByEmail(@Param("email") String email);

}
