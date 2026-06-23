package com.readstack.crud.discovery.search;

import com.readstack.crud.discovery.Discovery;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


public final class DiscoverySpecifications {
    public static Specification<Discovery> hasTitleLike(String title){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Discovery> hasCategory(String category){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("name"), category);
    }

    public static Specification<Discovery> createdBy(Long creatorId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("audit").get("creator"), creatorId);
    }

    public static Specification<Discovery> createdAfterThan(LocalDate date){
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("audit").get("created"), instant);
    }
    public static Specification<Discovery> createdBeforeThan(LocalDate date){
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("audit").get("created"), instant);
    }

    public static Specification<Discovery> voteCountGreaterThanOrEqualTo(Integer voteCount){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("voteCount"), voteCount);
    }


}
