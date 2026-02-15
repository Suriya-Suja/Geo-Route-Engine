package com.app.georoute.controllers;

import com.app.georoute.dtos.GetNearbyUserRequest;
import com.app.georoute.dtos.UserLocationRequest;
import com.app.georoute.dtos.UserLocationResponse;
import com.app.georoute.services.UserLocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-location")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserLocationController {

    /*
    TODO: This features are not utilized in frontend,
          Need work to be done
     */

    private final UserLocationService userLocationService;

    @PostMapping
    public ResponseEntity<UserLocationResponse> createUserLocation(@RequestBody UserLocationRequest request){

        var response = userLocationService.createUserLocation(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/saved-locations")
    public ResponseEntity<List<UserLocationResponse>> getSavedLocations(){
        List<UserLocationResponse> responses = userLocationService.getSavedLocations();
        return ResponseEntity.ok(responses);
    }
}
