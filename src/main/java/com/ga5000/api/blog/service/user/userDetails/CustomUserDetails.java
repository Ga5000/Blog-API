package com.ga5000.api.blog.service.user.userDetails;

import com.ga5000.api.blog.domain.user.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {
    private final UUID userId;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UUID userId, String username, Role role) {
        this.userId = userId;
        this.username = username;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    public UUID getUserId() {
        return userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return ""; // Google OAuth não exige senha
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}