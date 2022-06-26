package com.filmreview.dto;

import com.filmreview.domain.Film;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FilmDTO {
    private UUID id;
    private String imdbID;
    private String title;
    private List<ReviewDTO> reviews;

    public static FilmDTO of(Film film){
        var dto = new FilmDTO();
        dto.id = film.getId();
        dto.imdbID = film.getImdbID();
        dto.title = film.getTitle();
        dto.reviews = getReviews(film);

        return dto;
    }

    private static List<ReviewDTO> getReviews(Film film) {
        return film.getReviews()
                .stream()
                .map(ReviewDTO::of)
                .collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getTitle() {
        return title;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }
}
