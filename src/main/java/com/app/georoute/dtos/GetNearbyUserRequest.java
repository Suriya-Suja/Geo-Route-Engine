package com.app.georoute.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GetNearbyUserRequest {
    @NotNull(message = "Provide a range in meters")
    private double Radius;

    @NotNull(message = "Your location is not selected")
    @Size(min = -90, max = 90, message = "your location is invalid")
    private double latitude;

    @NotNull(message = "Your location is not selected")
    @Size(min = -180, max = 180, message = "your location is invalid")
    private double longitude;
}
