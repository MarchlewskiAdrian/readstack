package com.readstack.crud.discovery.search;

import java.time.LocalDate;

public record DiscoveryFilter(
        String title,
        String category,
        Long creatorId,
        Integer voteCount,
        LocalDate createdBefore,
        LocalDate createdAfter
) {
}
