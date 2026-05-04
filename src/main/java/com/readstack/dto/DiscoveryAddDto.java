package com.readstack.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record DiscoveryAddDto(
        @NotBlank(message = "Title cannot be null or blank")
        @Size(min = 5, max = 80, message = "Title must be between 5 and 80 characters")
        String title,


        @NotBlank(message = "URL cannot be null or blank")
        @Size(min = 5, max = 500, message = "URL must be between 5 and 500 characters")
        @URL(message = "Must by valid URL")
        String url,

        @NotBlank(message = "Description cannot be null or blank")
        @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
        String description,


        @NotNull(message = "Category id cannot be null")
        Long categoryId
) {
}
