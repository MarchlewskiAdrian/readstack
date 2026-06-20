package com.readstack.dto;

import java.util.Collection;

public record UserUpdateDto(
        String username,
        String email,
        boolean enabled,
        Collection<String> roles
) {
}
