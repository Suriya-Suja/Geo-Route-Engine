package com.app.georoute.dtos;

import lombok.Data;

@Data
public class PlaceResponse {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Double longitude;

    private Double latitude;
}
