package com.filmreview.domain.service;

import com.filmreview.domain.Film;
import com.filmreview.domain.Reviewer;
import com.filmreview.domain.dto.ReviewerDTO;
import com.filmreview.domain.repository.FilmRepository;
import com.filmreview.domain.repository.ReviewerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class FilmReviewService {

    private final FilmRepository filmRepository;
    private final ReviewerRepository reviewerRepository;

    public FilmReviewService(FilmRepository filmRepository, ReviewerRepository reviewerRepository) {
        this.filmRepository = filmRepository;
        this.reviewerRepository = reviewerRepository;
    }

    public List<Film> listAll(){
        return filmRepository.findAll();
    }

    public Film findById(UUID id) {
        return filmRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filme não encontrado"));
    }

//    public Film findById(UUID id) {
//        return films.stream().filter(film -> film.getExternalId().equals(id))
//                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filme não encontrado"));
//    }

    public Reviewer createReviewer(ReviewerDTO reviewer) {
        var newReview = Reviewer.of(reviewer.getName(), reviewer.getUsername(), reviewer.getEmail(), reviewer.getPassword());
        return reviewerRepository.save(newReview);
    }
}
