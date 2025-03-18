package com.ga5000.api.blog.service.token;


import com.ga5000.api.blog.service.user.userDetails.CustomUserDetails;

public interface ITokenService {
    String generateToken(CustomUserDetails user);
    String extractGoogleId(String token);
    boolean validateToken(String token);

}
