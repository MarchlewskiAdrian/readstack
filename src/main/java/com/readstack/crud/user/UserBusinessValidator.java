package com.readstack.crud.user;

import com.readstack.crud.ResourceType;
import com.readstack.crud.discovery.DiscoveryLookup;
import com.readstack.crud.vote.VoteLookup;
import com.readstack.validation.exception.UserHasRelatedDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserBusinessValidator {
    private final DiscoveryLookup discoveryLookup;
    private final VoteLookup voteLookup;

    void requireNoRelatedDiscoveries(Long userId) {
        if (discoveryLookup.hasUserDiscoveries(userId)) {
            throw new UserHasRelatedDataException(userId, ResourceType.DISCOVERY);
        }
    }

    void requireNoRelatedVotes(Long userId) {
        if (voteLookup.hasUserVotes(userId)) {
            throw new UserHasRelatedDataException(userId, ResourceType.VOTE);
        }
    }
}
