package com.app.georoute.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {

    @NotNull(message = "starting location is must")
    private Double fromLat;
    @NotNull(message = "starting location is must")
    private Double fromLon;

    @NotNull(message = "Destination is must")
    private Double toLat;
    @NotNull(message = "Destination is must")
    private Double toLon;

    // List of stops to make along the way
    @Size(max = 5)
    private List<List<Double>> waypoints;

    private String vehicle; // "car", "bike", "foot"
}
