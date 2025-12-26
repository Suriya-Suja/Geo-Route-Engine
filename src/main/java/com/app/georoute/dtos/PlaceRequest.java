package com.app.georoute.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PlaceRequest {

    private String name;
    private String description;
    private String imageUrl;

    private Double longitude;
    private Double latitude;



}
