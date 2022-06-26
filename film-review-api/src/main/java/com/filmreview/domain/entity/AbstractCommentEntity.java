package com.filmreview.domain.entity;

import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractCommentEntity extends AbstractEntity {

    protected String description;

    public String getDescription() {
        return description;
    }

}
