package com.ga5000.blog.api.data;

import com.ga5000.blog.api.models.UserModel;
import com.ga5000.blog.api.repositories.UserRepository;
import com.ga5000.blog.api.security.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

//Creates a default admin in the database
@Component
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Optional<UserDetails> adminUser = Optional.ofNullable(userRepository.findByUsername("admin"));
        if (adminUser.isEmpty()) {
            UserModel admin = new UserModel();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("123"));
            admin.setRole(UserRole.ADMIN);
            admin.setRegistrationDate(LocalDateTime.now());
            userRepository.save(admin);
        }
    }
}
