package com.readstack.user_crud;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAddDto(
        @NotBlank(message = "Username cannot be null or blank")
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        //TODO username unique validation
        String username,

        @NotBlank(message = "Email cannot be null or blank")
        @Email(message = "Must be valid email")
        @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters")
        //TODO email unique validation
        String email,

        @NotBlank(message = "Password cannot be null or blank")
        @Size(min = 5, max = 50, message = "Password must be between 5 and 50 characters")
        String password
) {
}
