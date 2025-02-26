package com.ga5000.api.blog.service.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ga5000.api.blog.domain.user.customUserDetails.CustomUserDetails;
import com.ga5000.api.blog.exception.auth.JWTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
    @Value(value = "${security.jwt,secret}")
    private String jwtSecret;



    public String generateToken(CustomUserDetails user) throws JWTException {
        try {
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationTime())
                    .sign(Algorithm.HMAC256(jwtSecret));
        } catch (JWTCreationException exception) {
            throw new JWTException("Failed to generate token.");
        }
    }

    public String extractUsername(String token) throws JWTException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTException("Invalid or expired token");
        }
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
