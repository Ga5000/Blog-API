package com.ga5000.api.blog.config.security.oauth2;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.token.TokenService;
import com.ga5000.api.blog.service.user.userDetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;
    private final TokenService tokenService;

    public OAuth2LoginSuccessHandler(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String googleId = oAuth2User.getAttribute("sub");
        if (googleId == null) {
            response.sendRedirect("/error");
            return;
        }

        User user = authService.registerUserIfNonExistent(oAuth2User, googleId);
        CustomUserDetails userDetails = new CustomUserDetails(user.getUserId(), user.getGoogleId(), user.getUsername(), user.getRole());

        String token = tokenService.generateToken(userDetails);

        response.setContentType("application/json");
        response.getWriter().write("{\"token\":\"" + token + "\"}");
    }
}