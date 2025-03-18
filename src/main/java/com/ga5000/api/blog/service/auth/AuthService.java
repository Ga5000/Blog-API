package com.ga5000.api.blog.service.auth;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.middleware.exception.auth.AuthenticationException;
import com.ga5000.api.blog.repository.user.UserRepository;
import com.ga5000.api.blog.service.token.TokenService;
import com.ga5000.api.blog.service.user.userDetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService implements IAuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthService(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User registerUserIfNonExistent(OAuth2User oAuth2User, String googleId) {
        return userRepository.findByGoogleId(googleId)
                .orElseGet(() -> {
                    String email = (String) oAuth2User.getAttributes().get("email");
                    String username = (String) oAuth2User.getAttributes().get("name");
                    String picture = (String) oAuth2User.getAttributes().get("picture");
                    return createUser(googleId, email, username, picture);
                });
    }

    @Override
    public UUID getCurrentUserId() throws AuthenticationException{
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new AuthenticationException("Invalid authentication");
        }
      return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }


    private User createUser(String googleId, String email, String username, String picture) {
        var newUser = new User(googleId, email, username, picture);
        return userRepository.save(newUser);
    }





}
