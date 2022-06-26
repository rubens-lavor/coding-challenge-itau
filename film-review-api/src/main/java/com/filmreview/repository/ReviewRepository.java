package com.filmreview.repository;

import com.filmreview.domain.Film;
import com.filmreview.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByReviewerIdAndFilmId(UUID reviewerId, UUID filmId);

}
