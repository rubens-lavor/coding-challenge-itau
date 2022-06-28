package com.filmreview.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractCommentEntity extends AbstractEntity {

    protected String description;

    protected LocalDateTime createdAt;

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
