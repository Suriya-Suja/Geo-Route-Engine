package com.app.georoute.services;

import com.app.georoute.dtos.GetNearbyUserRequest;
import com.app.georoute.dtos.UserLocationRequest;
import com.app.georoute.dtos.UserLocationResponse;
import com.app.georoute.entities.User;
import com.app.georoute.entities.UserLocation;
import com.app.georoute.mappers.UserLocationMapper;
import com.app.georoute.repositories.UserLocationRepository;
import com.app.georoute.security.CustomUserDetails;
import com.app.georoute.util.GeometryUtil;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserLocationService {

    private final UserLocationRepository userLocationRepository;
    private final UserLocationMapper userLocationMapper;
    private final UserService userService;

    public UserLocationResponse createUserLocation(UserLocationRequest request) {

            User user = userService.getCurrentUser();

            Point location = GeometryUtil.createPoint(request.getLongitude(), request.getLatitude());

            if(userLocationRepository.existsByUserAndLocation(user, location)){
                throw new RuntimeException("You have already saved this location!");
            }

            UserLocation userLocation = new UserLocation();
            userLocation.setLocationName(request.getLocationName());
            userLocation.setLocation(location);
            userLocation.setUser(user);
            userLocationRepository.save(userLocation);

            return userLocationMapper.toResponseDto(userLocation);
    }


    public List<UserLocationResponse> getSavedLocations() {

        User user = userService.getCurrentUser();
        List<UserLocation> userLocations = userLocationRepository.getUserLocationsByUser(user);
        return userLocationMapper.toResponseList(userLocations);
    }

}
