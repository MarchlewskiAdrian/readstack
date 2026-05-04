package com.readstack.dto;

public record DiscoveryGetDto(
        Long id,
        String title,
        String url,
        String description,
        CategoryNameDto category
) {
}
