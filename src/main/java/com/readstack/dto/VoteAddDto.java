package com.readstack.dto;

import com.readstack.crud.VoteType;

public record VoteAddDto(
        Long discoveryId,
        VoteType type
) {
}
