package com.ga5000.blog.api.controllers;

import com.ga5000.blog.api.dtos.AuthDTO;
import com.ga5000.blog.api.dtos.LoginResponseDTO;
import com.ga5000.blog.api.dtos.RegisterDTO;
import com.ga5000.blog.api.dtos.RegisterUserDTO;
import com.ga5000.blog.api.models.UserModel;
import com.ga5000.blog.api.repositories.UserRepository;
import com.ga5000.blog.api.security.TokenService;
import com.ga5000.blog.api.security.UserRole;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.username(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.username()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        var newUser = new UserModel(registerDTO.username(), encryptedPassword, registerDTO.role());
        newUser.setRegistrationDate(LocalDateTime.now());
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/register/user")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        if(userRepository.findByUsername(registerUserDTO.username()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        var newUser = new UserModel(registerUserDTO.username(),encryptedPassword);
        newUser.setRole(UserRole.USER);
        newUser.setRegistrationDate(LocalDateTime.now());
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}
