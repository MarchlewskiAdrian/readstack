package com.readstack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryAddDto(
        @NotBlank(message = "Name cannot be null or blank")
        @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
        String name,

        @NotBlank(message = "Description cannot be null or blank")
        @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
        String description
) {
}
