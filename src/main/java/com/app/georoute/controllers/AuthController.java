package com.app.georoute.controllers;

import com.app.georoute.dtos.CreateUserRequest;
import com.app.georoute.dtos.UserLoginRequest;
import com.app.georoute.entities.User;
import com.app.georoute.mappers.UserMapper;
import com.app.georoute.repositories.UserRepository;
import com.app.georoute.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    // TODO: create AuthService and clean up the controller

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {

        // TODO: Do proper error handling

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtService.generateToken(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody CreateUserRequest request){
        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if(user == null) {
            User newUser = userMapper.toEntity(request);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }
}
