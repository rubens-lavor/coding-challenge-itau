package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractCommentEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review extends AbstractCommentEntity {

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    private Boolean isRepeated = false;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @OneToMany(mappedBy = "review")
    private final List<ResponseReview> responses = new ArrayList<>();

}
