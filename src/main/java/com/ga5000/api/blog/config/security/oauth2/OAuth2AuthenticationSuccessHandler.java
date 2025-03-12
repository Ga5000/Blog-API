package com.ga5000.api.blog.config.security.oauth2;

import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.token.TokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final AuthService authService;

    public OAuth2AuthenticationSuccessHandler(TokenService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // Register the user if not exist
       authService.registerUserIfNonExistent();


        // Redirect to frontend with token
        String redirectUrl = "/api/auth/token";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}