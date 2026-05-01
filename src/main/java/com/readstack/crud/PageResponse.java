package com.readstack.crud;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        int totalPages,
        Long totalElements
) {
}

//class User {
//    Long id;
//    String name;
//
//}
//
//class Vote {
//    Discovery discovery;
//    User user;
//    VoteType type;
//
//}
//
//enum VoteType{
//    UP, DOWN
//}
//class Comment{
//    Discovery discovery;
//    User user;
//    String comment;
//}
//
//class Discovery {
//    private String title;
//    private String url;
//    private String description;
//    private Category category;
//    User creator;
//
//}