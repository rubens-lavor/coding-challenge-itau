package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imdbID;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private final List<Review> reviews = new ArrayList();

    public static Film of(String title, String imdbID) {
        var film = new Film();
        film.title = title;
        film.imdbID = imdbID;

        return film;
    }

    public Long getId() {
        return id;
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
