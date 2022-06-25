package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractCommentEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ResponseReview extends AbstractCommentEntity {

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

}
