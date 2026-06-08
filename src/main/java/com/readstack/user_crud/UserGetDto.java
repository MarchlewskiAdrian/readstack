package com.readstack.user_crud;

import java.util.Collection;

public record UserGetDto(
        Long id,
        String username,
        String email,
        boolean enabled,
        Collection<String> roles
) {
}
