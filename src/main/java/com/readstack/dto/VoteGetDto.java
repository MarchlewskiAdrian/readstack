package com.readstack.dto;

import com.readstack.crud.Audit;
import com.readstack.crud.VoteType;

public record VoteGetDto(
        Long id,
        Long userId,
        Long discoveryId,
        VoteType type,
        Audit audit
) {
}
