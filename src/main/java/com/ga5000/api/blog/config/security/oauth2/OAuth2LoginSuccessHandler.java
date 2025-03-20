package com.ga5000.api.blog.config.security.oauth2;

import com.ga5000.api.blog.domain.user.User;
import com.ga5000.api.blog.service.auth.AuthService;
import com.ga5000.api.blog.service.token.TokenService;
import com.ga5000.api.blog.service.user.userDetails.CustomUserDetails;
import jakarta.servlet.http.Cookie;
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
        String email = oAuth2User.getAttribute("email");

        User user = authService.registerUserIfNonExistent(oAuth2User, email);
        CustomUserDetails userDetails = new CustomUserDetails(user.getUserId(), user.getUsername(), user.getRole());

        String token = tokenService.generateToken(userDetails);

        Cookie cookie = new Cookie("AuthToken", token);
        cookie.setHttpOnly(false); 
        cookie.setSecure(false); // set to true if in production
        cookie.setPath("/");
        cookie.setMaxAge(48 * 60 * 60);

        response.addCookie(cookie);
        response.sendRedirect("/api/posts/search?page=0&direction=ASC");

    }
}