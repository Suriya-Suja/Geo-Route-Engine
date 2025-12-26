package com.app.georoute.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private double latitude;
    private double longitude;
}
