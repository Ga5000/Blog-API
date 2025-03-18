package com.ga5000.api.blog.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ga5000.api.blog.middleware.exception.token.TokenValidationException;
import com.ga5000.api.blog.service.user.userDetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService implements ITokenService {

    @Value("${security.jwt.secret}")
    private String secretKey;


    @Override
    public String generateToken(CustomUserDetails user) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getGoogleId())
                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .filter(role -> role.startsWith("ROLE_"))
                        .findFirst().orElse(null))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 48 * 60 * 60 * 1000)) // 48h
                .sign(algorithm);
    }

    @Override
    public String extractGoogleId(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        return decodedJWT.getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            return true;
        }catch (JWTVerificationException e){
            throw new TokenValidationException("Invalid token");
        }
    }



}
