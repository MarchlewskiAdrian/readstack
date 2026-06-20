package com.readstack.dto;

import com.readstack.crud.vote.VoteType;

public record VoteAddDto(
        Long discoveryId,
        VoteType type
) {
}
