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

    // ver a possibilidade de menções
//    @OneToMany(mappedBy = "mention")
//    private final List<Reviewer> reviewers = new ArrayList();


    protected String comment;

    protected Integer like = 0;

    protected Integer dislike = 0;

    public String getComment() {
        return comment;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getDislike() {
        return dislike;
    }

}
