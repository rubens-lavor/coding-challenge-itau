package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Film  extends AbstractEntity {

    private String title;

    private String imdbID;

    @OneToMany(mappedBy = "film")
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
        reviews.add(review);
    }
}
