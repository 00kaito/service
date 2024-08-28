package com.bednarz.usmobile.application.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(min = 3, message = "First name must be at least 3 characters long if provided")
    private String firstName;
    @Size(min = 3, message = "Last name must be at least 3 characters long if provided")
    private String lastName;
    @Size(min = 3, message = "Email name must be at least 3 characters long if provided")
    private String email;
}

