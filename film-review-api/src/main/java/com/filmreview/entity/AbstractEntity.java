package com.filmreview.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column(columnDefinition = "", length = 16, nullable = false)
    protected UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}
