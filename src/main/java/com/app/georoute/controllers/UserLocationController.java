package com.app.georoute.controllers;

import com.app.georoute.dtos.UserLocationRequest;
import com.app.georoute.dtos.UserLocationResponse;
import com.app.georoute.entities.User;
import com.app.georoute.entities.UserLocation;
import com.app.georoute.mappers.UserLocationMapper;
import com.app.georoute.repositories.UserLocationRepository;
import com.app.georoute.repositories.UserRepository;
import com.app.georoute.util.GeometryUtil;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-location")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserLocationController {

    private final UserLocationRepository userLocationRepository;
    private final UserLocationMapper userLocationMapper;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserLocationResponse> createUserLocation(@RequestBody UserLocationRequest request){

        User user = userRepository.findById(request.getUserId())
                .orElse(null);

        if(user == null){
            return ResponseEntity.badRequest().build();
        }


        Point location = GeometryUtil.createPoint(request.getLongitude(), request.getLatitude());

        UserLocation userLocation = new UserLocation();
        userLocation.setLocationName(request.getName());
        userLocation.setLocation(location);
        userLocation.setUser(user);
        userLocationRepository.save(userLocation);

        return ResponseEntity.ok(userLocationMapper.toResponseDto(userLocation));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<UserLocationResponse>> getNearByUser(
        @RequestParam double lat,
        @RequestParam double lon,
        @RequestParam double radius //in meters
    ){
        Point location = GeometryUtil.createPoint(lon, lat);
        var userList = userLocationRepository.findUsersWithinRadius(location, radius);

        return ResponseEntity.ok(userLocationMapper.toResponseList(userList));
    }
}
