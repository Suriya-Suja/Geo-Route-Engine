package com.app.georoute.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
}
