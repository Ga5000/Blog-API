package com.ga5000.blog.api.controllers;

import com.ga5000.blog.api.models.UserModel;
import com.ga5000.blog.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable UUID id) {
        Optional<UserModel> userToDelete = userService.findUserById(id);
        if (userToDelete.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication.getName();


        if (!userToDelete.get().getId().toString().equals(authenticatedUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated");
        }

        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
}
