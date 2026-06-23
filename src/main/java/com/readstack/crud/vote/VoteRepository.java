package com.readstack.crud.vote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface VoteRepository extends JpaRepository<Vote, Long>, JpaSpecificationExecutor<Vote> {
    @EntityGraph("Vote.userAndDiscovery")
    Page<Vote> findAll(Specification specification,Pageable pageable);

    boolean existsByAudit_Creator(Long creator);
}
