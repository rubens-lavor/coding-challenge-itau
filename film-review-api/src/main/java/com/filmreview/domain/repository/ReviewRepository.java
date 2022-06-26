package com.filmreview.domain.repository;

import com.filmreview.domain.Film;
import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByReviewerIdAndFilmId(Long reviewerId, Long filmId);

    Optional<Review> findByReviewerId(Long reviewerId);

    Optional<Review> findByFilmId(Long id);

    Optional<Review> findByFilm(Film film);
}
