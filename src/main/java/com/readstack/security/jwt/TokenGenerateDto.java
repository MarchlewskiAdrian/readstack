package com.readstack.security.jwt;

import jakarta.validation.constraints.NotBlank;

public record TokenGenerateDto(
        @NotBlank String username,
        @NotBlank String password
) {
}
