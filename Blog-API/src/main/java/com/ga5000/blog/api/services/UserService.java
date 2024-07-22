package com.ga5000.blog.api.services;


import com.ga5000.blog.api.models.UserModel;
import com.ga5000.blog.api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserModel> findUserById(UUID id){return userRepository.findById(id);}

    @Transactional
    public void deleteById(UUID id){
        userRepository.deleteById(id);
    }


}
