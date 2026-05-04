package com.readstack.crud;

import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryRepository extends JpaRepository<Category, Long> {

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