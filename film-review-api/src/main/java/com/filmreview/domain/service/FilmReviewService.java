package com.filmreview.domain.service;

import com.filmreview.domain.Film;
import com.filmreview.domain.Grade;
import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;
import com.filmreview.domain.dto.FilmDTO;
import com.filmreview.domain.dto.ReviewDTO;
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

    public ReviewerDTO createReviewer(ReviewerDTO dto) {
        var reviewer = Reviewer.of(dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPassword());
        return ReviewerDTO.of(reviewerRepository.save(reviewer));
    }

    public FilmDTO sendReview(UUID id, ReviewDTO dto) {
        var film = getFilm(id);
        var reviewer = reviewerRepository.findById(dto.getReviewer().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));

        // var grade = Grade.of(reviewer, dto.); -->> provavelmente a classe grade não será mais necessária
        var review = Review.of(reviewer, dto.getComment());

        // film.addGrade()
        film.addReview(review);

        filmRepository.save(film);
        reviewer.addExperience();

        return FilmDTO.of(film);

    }

    private Film getFilm(UUID id) {
        var optionalFilm = filmRepository.findById(id);
        // TODO:
        // aqui se não encontrar o filme na nossa base, chama a api externa
        // é  importante tratar possíveis exceções vinda da api, caso não encontre o filme lá tbm
        // criar o filme com externalId e title
        return optionalFilm.orElseGet(Film::new);
    }
}
