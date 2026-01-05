package com.app.georoute.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotNull(message = "username cannot be empty")
    @Size(max = 255, message = "username must contain less than 255 characters")
    private String username;

    @Email(message = "enter a valid email")
    @NotNull(message = "email cannot be empty")
    private String email;

    @NotNull(message = "password cannot be empty")
    @Size(min = 8, max = 25, message = "password must contain between 8 and 25 characters")
    private String password;

}
