package com.filmreview.domain;

import com.filmreview.entity.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Films")
public class Film extends  AbstractEntity {

    private String title;

    @Column(name = "imdb_ID")
    private String imdbID;

    private Double rating = 0.0;

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

    public Double getRating() {
        return rating;
    }

    public void addReview(Review review) {
        if (reviews.contains(review)) return;
        reviews.add(review);
    }

    public void updateRating() {
        var totalPoints = reviews.stream()
                .map(Review::getGrade)
                .reduce(0.0, Double::sum);

        rating = totalPoints / reviews.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title)
                && Objects.equals(imdbID, film.imdbID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imdbID, reviews);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
