package com.readstack.dto;

import com.readstack.crud.Audit;

public record DiscoveryGetDto(
        Long id,
        String title,
        String url,
        String description,
        CategoryNameDto category,
        Audit audit
) {
}
