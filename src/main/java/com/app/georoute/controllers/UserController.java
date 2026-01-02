package com.app.georoute.controllers;

import com.app.georoute.entities.User;
import com.app.georoute.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User>  getUser(@RequestParam Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


}
