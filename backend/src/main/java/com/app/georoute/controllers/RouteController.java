package com.app.georoute.controllers;

import com.app.georoute.dtos.RouteRequest;
import com.app.georoute.dtos.RouteResponse;
import com.app.georoute.services.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/route")
@CrossOrigin(origins = "*")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> getRoute(@RequestBody RouteRequest request) {
        var response = routeService.getRoute(request);
        return ResponseEntity.ok(response);
    }
}
