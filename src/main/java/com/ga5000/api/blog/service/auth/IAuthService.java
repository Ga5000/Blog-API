package com.ga5000.api.blog.service.auth;


import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.middleware.exception.auth.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;

public interface IAuthService {
    User registerUserIfNonExistent(OAuth2User oAuth2User, String googleId);
    UUID getCurrentUserId() throws AuthenticationException;
}
