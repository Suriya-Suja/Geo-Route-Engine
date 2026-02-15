package com.app.georoute.controllers;

import com.app.georoute.dtos.CreateUserRequest;
import com.app.georoute.dtos.UserLoginRequest;
import com.app.georoute.dtos.UserResponse;
import com.app.georoute.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {

        String token = authService.Login(request);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody CreateUserRequest request){
        UserResponse response = authService.signUp(request);
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }
}
