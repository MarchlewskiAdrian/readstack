package com.readstack.crud.vote;

import com.readstack.crud.BaseEntity;
import com.readstack.crud.discovery.Discovery;
import com.readstack.crud.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "votes")
@NamedEntityGraph(
        name = "Vote.userAndDiscovery",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("discovery")
        }
)
public class Vote extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Discovery discovery;
    @Enumerated(EnumType.STRING)
    private VoteType type;
}
