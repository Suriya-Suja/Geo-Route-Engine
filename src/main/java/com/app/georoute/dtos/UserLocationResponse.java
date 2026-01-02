package com.app.georoute.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLocationResponse {
    private Long id;
    private String locationName;
    private double latitude;
    private double longitude;
}
