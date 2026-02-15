package com.app.georoute.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {
    private double totalDistanceMeters;
    private long totalTimeMilliseconds;

    private List<List<Double>> waypoints;
}
