package com.app.georoute.dtos;

import lombok.Data;

@Data
public class UserLocationRequest {
    private Long userId;
    private String name;
    private double latitude;
    private double longitude;
}
