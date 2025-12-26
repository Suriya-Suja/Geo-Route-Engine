package com.app.georoute.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {
    private Double fromLat;
    private Double fromLon;
    private Double toLat;
    private Double toLon;

    // List of stops to make along the way
    private List<List<Double>> waypoints;

    private String vehicle; // "car", "bike", "foot"
}
