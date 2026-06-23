package com.readstack.crud.discovery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface DiscoveryRepository extends JpaRepository<Discovery, Long>, JpaSpecificationExecutor<Discovery> {

    boolean existsByTitleIgnoreCaseAndIdNot(String title, Long id);

    @EntityGraph(attributePaths = "category")
    Page<Discovery> findAll(Specification<Discovery> spec, Pageable pageable);

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByCategory_Id(Long categoryId);

    @EntityGraph(attributePaths = "category")
    Page<Discovery> findAllByCategory_Id(Long categoryId, Pageable pageable);

    boolean existsByUrlIgnoreCase(String url);

    boolean existsByUrlIgnoreCaseAndIdNot(String url, Long id);

    boolean existsByAudit_creator(Long creator);
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