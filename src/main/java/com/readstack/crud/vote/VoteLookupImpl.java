package com.readstack.crud.vote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class VoteLookupImpl implements VoteLookup {
    private final VoteFetcher voteFetcher;

    @Override
    public boolean hasUserVotes(Long userId) {
        return voteFetcher.existsByUserId(userId);
    }

}
