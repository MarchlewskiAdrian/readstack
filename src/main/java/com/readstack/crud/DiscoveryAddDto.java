package com.readstack.crud;

public record DiscoveryAddDto(
        String title,
        String url,
        String description,
        Long categoryId
) {
}
