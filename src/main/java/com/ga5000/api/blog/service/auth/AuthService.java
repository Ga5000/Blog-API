package com.ga5000.api.blog.service.auth;
import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.repository.user.UserRepository;
import com.ga5000.api.blog.service.auth.oauth2.CustomOauth2Service;
import com.ga5000.api.blog.service.token.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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
        String googleId = oidcUser.getAttribute("sub"); // googleId
        Optional<User> user = userRepository.findByGoogleId(googleId);
        if (user.isEmpty()){
            String email = oidcUser.getAttribute("email");
            String username = oidcUser.getAttribute("name");
            String picture = oidcUser.getAttribute("picture");
            createUser(googleId, email, username, picture);
        }
    }

    @Override
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return tokenService.generateToken(authentication);
    }


    private OidcUser getOidcUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (OidcUser) authentication.getPrincipal();
    }

    private Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    private void createUser(String googleId, String email, String username, String profilePicture) {
        User newUser = new User(googleId, email, username, profilePicture);
        userRepository.save(newUser);
    }
}
