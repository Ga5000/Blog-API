package com.ga5000.api.blog.config.security;

import com.ga5000.api.blog.middleware.filter.JwtAuthenticationFilter;

import com.ga5000.api.blog.config.security.oauth2.OAuth2LoginSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2LoginSuccessHandler oAuth2AuthenticationSuccessHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, OAuth2LoginSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/oauth2/success").permitAll()
                        .requestMatchers("/login/**").permitAll() // Allow OAuth2 login endpoints
                        .requestMatchers("/oauth2/**").permitAll() // Allow OAuth2 endpoints
                        .requestMatchers(HttpMethod.POST, "/api/posts").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/posts/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/categories").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comments/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/comments/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2AuthenticationSuccessHandler))
                // Keep sessions only for the OAuth2 login flow
                .securityContext(security -> security
                        .securityContextRepository(new HttpSessionSecurityContextRepository()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true))
                .build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}