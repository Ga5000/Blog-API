package com.ga5000.api.blog.service.auth;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.exception.auth.AuthenticationException;
import com.ga5000.api.blog.repository.user.UserRepository;
import com.ga5000.api.blog.service.token.TokenService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void registerUserIfNonExistent() {
        OidcUser oidcUser = getOidcUser();
        String googleId = oidcUser.getAttribute("sub");

        userRepository.findByGoogleId(googleId)
                .ifPresentOrElse(
                        user -> {
                            // User exists; no action required.
                        },
                        () -> {
                            String email = oidcUser.getAttribute("email");
                            String username = oidcUser.getAttribute("name");
                            String picture = oidcUser.getAttribute("picture");
                            createUser(googleId, email, username, picture);
                        }
                );
    }

    @Override
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return tokenService.generateToken(authentication);
    }

    @Override
    public User getCurrentUser() throws EntityNotFoundException {
        OidcUser oidcUser = getOidcUser();
        String googleId = oidcUser.getAttribute("sub");

        return userRepository.findByGoogleId(googleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found")
                );
    }

    private OidcUser getOidcUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof OidcUser) {
            return (OidcUser) principal;
        }
        throw new AuthenticationException("No authenticated user found");
    }

    private void createUser(String googleId, String email, String username, String profilePicture) {
        User newUser = new User(googleId, email, username, profilePicture);
        userRepository.save(newUser);
    }
}
