package com.app.georoute.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PlaceRequest {

    @NotNull(message = "name is required")
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Image url must be given")
    private String imageUrl;

    @NotNull(message = "location must be given")
    @Size(min = -180, max = 180)
    private Double longitude;

    @NotNull(message = "location must be given")
    @Size(min = -90, max = 90)
    private Double latitude;

}
