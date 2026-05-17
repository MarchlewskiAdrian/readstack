package com.readstack.crud;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface DiscoveryRepository extends JpaRepository<Discovery, Long> {

    boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id);

    @EntityGraph(attributePaths = "category")
    @Query("""
            SELECT d FROM Discovery d
            WHERE (LOWER(d.title) LIKE LOWER(CONCAT('%', :title, '%')) OR :title IS NULL)
            """)
    Page<Discovery> findAllWitOptionalTitleField(String title, Pageable pageable);

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByCategory_Id(Long categoryId);

    @EntityGraph(attributePaths = "category")
    Page<Discovery> findAllByCategory_Id(Long categoryId, Pageable pageable);

    boolean existsByUrlIgnoreCase(String url);

    boolean existsByUrlIgnoreCaseAndIdNot(String url, Long id);
}


// === FETCH JOIN vs ENTITY GRAPH ===
//
// 1. Page + relacje
//    Najczęściej używamy:
//    Page<T> + @EntityGraph
//
//    Powód:
//    JOIN FETCH często źle współpracuje z paginacją
//    (LIMIT / OFFSET / COUNT)
//
// --------------------------------------------------
//
// 2. List + bez paginacji
//    Można używać:
//    List<T> + JOIN FETCH
//
//    Powód:
//    brak problemów z paginacją,
//    dobre rozwiązanie do pobrania pełnej listy
//
// --------------------------------------------------
//
// 3. Najważniejsza zasada
//
//    Problemem nie jest @Query,
//    tylko:
//
//    JOIN FETCH + Page<T>
//
//    tego najczęściej należy unikać
//
// --------------------------------------------------
//
// 4. @Query i @EntityGraph można łączyć
//
//    np.
//    @EntityGraph(attributePaths = "category")
//    @Query(...)
//
//
// --------------------------------------------------
//
// 5. Przy @OneToMany problem jest jeszcze większy
//
//    FETCH JOIN + Page
//    prawie zawsze należy unikać