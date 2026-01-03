package com.app.georoute.controllers;

import com.app.georoute.dtos.UserResponse;
import com.app.georoute.entities.User;
import com.app.georoute.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>  getUser(@RequestParam Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }


}
