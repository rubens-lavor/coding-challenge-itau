package com.filmreview.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractCommentEntity extends AbstractEntity {

    protected String description;

    public String getDescription() {
        return description;
    }

}
