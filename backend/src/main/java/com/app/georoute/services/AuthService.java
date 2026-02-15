package com.app.georoute.services;

import com.app.georoute.dtos.CreateUserRequest;
import com.app.georoute.dtos.UserLoginRequest;
import com.app.georoute.dtos.UserResponse;
import com.app.georoute.entities.User;
import com.app.georoute.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public String Login(UserLoginRequest request){
        User user = userService.getUserByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }

        return jwtService.generateToken(user);
    }

    public UserResponse signUp(CreateUserRequest request){
        return userService.createUser(request);
    }
}
