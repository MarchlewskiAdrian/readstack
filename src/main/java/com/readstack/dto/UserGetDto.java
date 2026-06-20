package com.readstack.dto;

import java.util.Collection;

public record UserGetDto(
        Long id,
        String username,
        String email,
        boolean enabled,
        Collection<String> roles
) {
}
