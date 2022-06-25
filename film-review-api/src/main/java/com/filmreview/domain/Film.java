package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Film  extends AbstractEntity {

    private UUID externalId;

    private String title;

    @OneToMany(mappedBy = "film")
    private final List<Grade> grades = new ArrayList();

    @OneToMany(mappedBy = "film")
    private final List<Review> reviews = new ArrayList();

    public UUID getExternalId() {
        return externalId;
    }

    public String getTitle() {
        return title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Grade> getGrades() {
        return grades;
    }


    public void addReview(Review review) {
        reviews.add(review);
    }
}
