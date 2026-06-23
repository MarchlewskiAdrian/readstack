package com.readstack.crud.vote.search;

import com.readstack.crud.vote.Vote;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VoteSpecificationsBuilder {
    public Specification<Vote> build(VoteFilter filter) {
        Specification<Vote> spec = Specification.unrestricted();
        if (filter.discoveryId() != null) {
            spec = VoteSpecifications.hasDiscoveryId(filter.discoveryId());
        }
        if (filter.userId() != null) {
            spec = VoteSpecifications.hasUserId(filter.userId());
        }
        if (filter.type() != null) {
            spec = VoteSpecifications.hasType(filter.type());
        }
        if (filter.createdBefore() != null) {
            spec = VoteSpecifications.createdBeforeThan(filter.createdBefore());
        }
        if (filter.createdAfter() != null) {
            spec = VoteSpecifications.createdAfterThan(filter.createdAfter());
        }

        return spec;
    }
}
