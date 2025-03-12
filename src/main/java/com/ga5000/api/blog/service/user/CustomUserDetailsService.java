package com.ga5000.api.blog.service.user;


import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.repository.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList
                (new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "", // Password is not used with OAuth2
                authorities
        );
    }
}