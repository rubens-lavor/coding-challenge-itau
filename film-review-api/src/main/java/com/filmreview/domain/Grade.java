package com.filmreview.domain;

import com.filmreview.domain.dto.GradeDTO;
import com.filmreview.domain.dto.ReviewerDTO;
import com.filmreview.domain.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Grade extends AbstractEntity { // TODO: considerar mudar o nome da classe
    // TODO: deletar a classe!

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    private Double grade;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public static Grade of(Reviewer reviewer, Double grade){
        var newGrade = new Grade();
        newGrade.reviewer = reviewer;
        newGrade.grade = grade;

        return newGrade;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Double getGrade() {
        return grade;
    }

    public Film getFilm() {
        return film;
    }

    public void addFilm(Film film) {
        this.film = film;
    }
}
