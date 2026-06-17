package com.readstack.crud;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Embeddable
@Getter
@Setter
public class Audit {
    @CreatedBy
    private Long creator;
    @LastModifiedBy
    private Long updater;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant updated;
}

