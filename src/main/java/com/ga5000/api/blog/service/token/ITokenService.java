package com.ga5000.api.blog.service.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface ITokenService {
    String generateToken(Authentication authentication);
    String extractUsername(String token);
    Date extractExpiration(String token);
    Boolean isTokenExpired(String token);
    Boolean validateToken(String token, UserDetails userDetails);
    DecodedJWT decodeToken(String token);

}
