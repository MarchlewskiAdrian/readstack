package com.readstack.crud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Discovery extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}

// Relacja ManyToOne / OneToMany:
// Discovery jest właścicielem relacji, ponieważ posiada pole:
// @ManyToOne
// private Category category;
// To oznacza, że wiele Discovery może należeć do jednej Category.
// W tabeli discovery powstaje klucz obcy:
// category_id -> category.id
// Encja Category posiada drugą stronę relacji:
// @OneToMany(mappedBy = "category")
// private List<Discovery> discoveries;
// mappedBy = "category" oznacza, że relacja jest zarządzana
// przez pole category w encji Discovery.
// @OneToMany nie tworzy nowej kolumny w bazie —
// tylko odwzorowuje relację od drugiej strony.