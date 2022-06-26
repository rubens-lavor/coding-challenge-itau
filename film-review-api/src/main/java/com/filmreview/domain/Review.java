package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Review extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private Double grade;

    //@ManyToOne(optional = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id")
    private Film film;

    public static Review of(Reviewer reviewer, Film film) {
        var review = new Review();
        review.reviewer = reviewer;
        review.film = film;

        return review;
    }

    public static Review of(Reviewer reviewer) {
        var review = new Review();
        review.reviewer = reviewer;

        return review;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Comment getComment() {
        return comment;
    }

    public Double getGrade() {
        return grade;
    }

    public Film getFilm() {
        return film;
    }

    public void addGrade(Double grade) {
        this.grade = grade;
    }

    public void addComment(Comment comment) {
        this.comment = comment;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
