package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractCommentEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Review extends AbstractCommentEntity {

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    private Double grade;

    private Boolean isRepeated = false;

    @OneToMany(mappedBy = "review")
    private final List<ResponseReview> responses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public static Review of(Reviewer reviewer, String comment, Double grade) {
        var review = new Review();
        review.reviewer = reviewer;
        review.comment = comment;
        review.grade = grade;

        return review;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Double getGrade() {
        return grade;
    }

    public Boolean getRepeated() {
        return isRepeated;
    }

    public Film getFilm() {
        return film;
    }

    public List<ResponseReview> getResponses() {
        return responses;
    }

    public void repeatedReview(){
        isRepeated = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewer, review.reviewer) && Objects.equals(isRepeated, review.isRepeated) && Objects.equals(film, review.film) && Objects.equals(responses, review.responses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewer, isRepeated, film, responses);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewer=" + reviewer +
                ", grade=" + grade +
                ", isRepeated=" + isRepeated +
                ", responses=" + responses +
                ", film=" + film +
                ", comment='" + comment + '\'' +
                ", like=" + like +
                ", dislike=" + dislike +
                '}';
    }
}
