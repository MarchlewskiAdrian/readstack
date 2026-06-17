package com.readstack.crud;

import com.readstack.user_crud.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "votes")
class Vote extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Discovery discovery;
    @Enumerated(EnumType.STRING)
    private VoteType type;
}
