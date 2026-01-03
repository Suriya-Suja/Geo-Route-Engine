package com.app.georoute.services;

import com.app.georoute.dtos.CreateUserRequest;
import com.app.georoute.dtos.UserResponse;
import com.app.georoute.entities.User;
import com.app.georoute.mappers.UserMapper;
import com.app.georoute.repositories.UserRepository;
import com.app.georoute.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID "+userId+" is not found"));
        return userMapper.toDto(user);
    }

    public UserResponse getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof CustomUserDetails){
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userMapper.toDto(userDetails.getUser());
        }

        throw new RuntimeException("User not authenticated");
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
    }

    public UserResponse createUser(CreateUserRequest request){
        if(!userRepository.existsUserByEmail(request.getEmail())){
            User newUser = userMapper.toEntity(request);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return userMapper.toDto(newUser);
        }
        return null;
    }
}
