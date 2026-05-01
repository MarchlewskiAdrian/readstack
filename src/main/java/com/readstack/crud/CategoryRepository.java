package com.readstack.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("""
            SELECT c FROM Category c
            """)
    Page<Category> findAll(Pageable pageable);

    boolean existsByNameIgnoreCase(String name);
}


//@Modifying
//@Query("""
//    DELETE FROM Category c
//    WHERE c.id = :id
//    AND NOT EXISTS (
//        SELECT 1 FROM Discovery d WHERE d.category.id = :id
//    )
//""")
//int deleteIfEmpty(Long id);