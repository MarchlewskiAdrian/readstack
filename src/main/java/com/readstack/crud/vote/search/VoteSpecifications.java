package com.readstack.crud.vote.search;

import com.readstack.crud.vote.Vote;
import com.readstack.crud.vote.VoteType;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class VoteSpecifications {
    public static Specification<Vote> hasDiscoveryId(Long discoveryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("discovery").get("id"), discoveryId);
    }

    public static Specification<Vote> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Vote> hasType(VoteType type) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Vote> createdAfterThan(LocalDate date) {
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("audit").get("created"), instant);
    }

    public static Specification<Vote> createdBeforeThan(LocalDate date) {
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("audit").get("created"), instant);
    }
}
