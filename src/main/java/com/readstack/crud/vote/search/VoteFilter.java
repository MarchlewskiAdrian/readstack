package com.readstack.crud.vote.search;

import com.readstack.crud.vote.VoteType;

import java.time.LocalDate;

public record VoteFilter(
        Long discoveryId,
        Long userId,
        VoteType type,
        LocalDate createdBefore,
        LocalDate createdAfter
) {
}
