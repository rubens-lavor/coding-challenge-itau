package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Grade  extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    private Double grade;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
