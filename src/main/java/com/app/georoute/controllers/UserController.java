package com.app.georoute.controllers;

import com.app.georoute.dtos.UserRequest;
import com.app.georoute.dtos.UserResponse;
import com.app.georoute.entities.UserLocation;
import com.app.georoute.mappers.UserLocationMapper;
import com.app.georoute.repositories.UserRepository;
import com.app.georoute.util.GeometryUtil;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final UserLocationMapper userLocationMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){

        Point location = GeometryUtil.createPoint(request.getLongitude(), request.getLatitude());

        UserLocation user = new UserLocation();
        user.setName(request.getName());
        user.setLocation(location);
        userRepository.save(user);

        return ResponseEntity.ok(userLocationMapper.toResponseDto(user));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<UserResponse>> getNearByUser(
        @RequestParam double lat,
        @RequestParam double lon,
        @RequestParam double radius //in meters
    ){
        Point location = GeometryUtil.createPoint(lon, lat);
        var userList = userRepository.findUsersWithinRadius(location, radius);

        return ResponseEntity.ok(userLocationMapper.toResponseList(userList));
    }
}
