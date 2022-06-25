package com.filmreview.domain.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    protected UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}
