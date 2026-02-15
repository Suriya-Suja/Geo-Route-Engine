package com.app.georoute.services;

import com.app.georoute.entities.User;
import com.app.georoute.entities.UserCurrentLocation;
import com.app.georoute.repositories.UserCurrentLocationRepository;
import com.app.georoute.util.GeometryUtil;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCurrentLocationService {

    private final UserCurrentLocationRepository userCurrentLocationRepository;
    private final UserService userService;

    public UserCurrentLocation updateLocation(double lat, double lon) {
        User user = userService.getCurrentUser();
        Point currentPoint = GeometryUtil.createPoint(lon, lat);

        UserCurrentLocation currentLocation = userCurrentLocationRepository.findById(user.getId())
                .orElse(new UserCurrentLocation());


        currentLocation.setUser(user);
        currentLocation.setLastKnownLocation(currentPoint);

        return userCurrentLocationRepository.save(currentLocation);
    }
}
