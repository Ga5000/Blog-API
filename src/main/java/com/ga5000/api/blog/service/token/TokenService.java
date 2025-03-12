package com.ga5000.api.blog.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ga5000.api.blog.exception.token.TokenValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService implements ITokenService {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(Authentication authentication) {
        String subject;
        String name = null;
        String googleId = null;

        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            subject = oidcUser.getAttribute("email");
            name = oidcUser.getAttribute("name");
            googleId = oidcUser.getAttribute("sub");
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            subject = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            subject = authentication.getName();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(subject)
                .withClaim("name", name)
                .withClaim("googleId", googleId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() +  48 * 60 * 60 * 1000)) //48h -> 2 days
                .sign(algorithm);
    }


    @Override
    public String extractUsername(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        return jwt.getSubject();
    }

    @Override
    public Date extractExpiration(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        return jwt.getExpiresAt();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public DecodedJWT decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
    }
}
