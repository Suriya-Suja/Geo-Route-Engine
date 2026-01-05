package com.app.georoute.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateImageUrlRequest {

    @NotNull(message = "place Id is required")
    long id;

    @NotNull(message = "New image URL is not provided")
    String imageUrl;
}
