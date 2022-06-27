package com.filmreview.domain;

import com.filmreview.entity.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Films")
public class Film extends  AbstractEntity {

    private String title;

    @Column(name = "imdb_ID")
    private String imdbID;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Review> reviews = new ArrayList();

    public static Film of(String title, String imdbID) {
        var film = new Film();
        film.title = title;
        film.imdbID = imdbID;

        return film;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getTitle() {
        return title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        if (reviews.contains(review)) return;
        reviews.add(review);
    }
}
