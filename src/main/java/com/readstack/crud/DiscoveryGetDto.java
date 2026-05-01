package com.readstack.crud;

public record DiscoveryGetDto(
        Long id,
        String title,
        String url,
        String description,
        CategoryNameDto category
) {
}
