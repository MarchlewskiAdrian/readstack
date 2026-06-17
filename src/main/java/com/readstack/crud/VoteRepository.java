package com.readstack.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VoteRepository extends JpaRepository<Vote, Long> {

    Page<Vote> findAllByDiscovery_Id(Long discoveryId, Pageable pageable);
    Page<Vote> findAllByUser_Id(Long userId, Pageable pageable);
    Page<Vote> findAllByUser_IdAndDiscovery_Id(Long userId, Long discoveryId, Pageable pageable);
}
