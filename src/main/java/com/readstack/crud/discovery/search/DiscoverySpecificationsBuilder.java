package com.readstack.crud.discovery.search;

import com.readstack.crud.discovery.Discovery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DiscoverySpecificationsBuilder {
    public Specification<Discovery> build(DiscoveryFilter filter) {

        Specification<Discovery> spec = Specification.unrestricted();

        if (filter.title() != null) {
            spec = spec.and(DiscoverySpecifications.hasTitleLike(filter.title()));
        }
        if (filter.category() != null) {
            spec = spec.and(DiscoverySpecifications.hasCategory(filter.category()));
        }
        if (filter.creatorId() != null) {
            spec = spec.and(DiscoverySpecifications.createdBy(filter.creatorId()));
        }
        if (filter.createdBefore() != null) {
            spec = spec.and(DiscoverySpecifications.createdBeforeThan(filter.createdBefore()));
        }
        if (filter.createdAfter() != null) {
            spec = spec.and(DiscoverySpecifications.createdAfterThan(filter.createdAfter()));
        }
        if (filter.voteCount() != null) {
            spec = spec.and(DiscoverySpecifications.voteCountGreaterThanOrEqualTo(filter.voteCount()));
        }

        return spec;
    }

}
