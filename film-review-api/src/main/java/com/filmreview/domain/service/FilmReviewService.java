package com.filmreview.domain.service;

import com.filmreview.domain.Film;
import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;
import com.filmreview.domain.dto.FilmDTO;
import com.filmreview.domain.dto.ResponseBodyOMDd;
import com.filmreview.domain.dto.ReviewDTO;
import com.filmreview.domain.dto.ReviewerDTO;
import com.filmreview.domain.repository.FilmRepository;
import com.filmreview.domain.repository.ReviewRepository;
import com.filmreview.domain.repository.ReviewerRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FilmReviewService {

    private final FilmRepository filmRepository;
    private final ReviewerRepository reviewerRepository;
    private final ReviewRepository reviewRepository;
    private final RestTemplateBuilder restTemplateBuilder;

    public FilmReviewService(FilmRepository filmRepository,
                             ReviewerRepository reviewerRepository,
                             ReviewRepository reviewRepository,
                             RestTemplateBuilder restTemplateBuilder
    ) {
        this.filmRepository = filmRepository;
        this.reviewerRepository = reviewerRepository;
        this.reviewRepository = reviewRepository;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<FilmDTO> listAll(){
        return filmRepository.findAll()
                .stream()
                .map(FilmDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public FilmDTO findByImdbID(String imdbID) {
        var url = "http://www.omdbapi.com/?i=" + imdbID + "&apikey=a5180135";
        var responseBodyOMDd = getResponseBodyOMDd(url);
        var film = Film.of(responseBodyOMDd.Title, responseBodyOMDd.imdbID);
        return FilmDTO.of(filmRepository.save(film));
    }

    private ResponseBodyOMDd getResponseBodyOMDd(String url) {
        var objectResponse = restTemplateBuilder.build().getForObject(url, ResponseBodyOMDd.class);

        assert Objects.nonNull(objectResponse);
        if (objectResponse.Response.equals("False")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, objectResponse.Error);
        }
        return objectResponse;
    }

//    public Film findById(UUID id) {
//        return filmRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filme não encontrado"));
//    }

    @Transactional
    public ReviewerDTO createReviewer(ReviewerDTO dto) {
        var reviewer = Reviewer.of(dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPassword());
        return ReviewerDTO.of(reviewerRepository.save(reviewer));
    }

    @Transactional
    public FilmDTO sendReview(String imdbID, ReviewDTO dto) {
        var film = getFilm(imdbID);
        var reviewer = getReviewer(dto);
        var review = Review.of(reviewer, dto.getComment(), dto.getGrade());
        reviewRepository.save(review);

        film.addReview(review);
        filmRepository.save(film);

        // tenho que adicionar film em review e salvar novamente??

        reviewer.addExperience();
        reviewerRepository.save(reviewer);

        return FilmDTO.of(film);

    }

    private Reviewer getReviewer(ReviewDTO dto) {
        var reviewer = reviewerRepository.findById(dto.getReviewer().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));

        if (reviewer.getProfileType().isReader() && Objects.nonNull(dto.getComment())) {
            //TODO: verificar se BAD_REQUEST faz sentido
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "READER cannot leave comments");
        }
        return reviewer;
    }

    private Film getFilm(String imdbID) {
        var optionalFilm = filmRepository.findByImdbID(imdbID);
        if (optionalFilm.isPresent()) {
            return optionalFilm.get();
        }
        var filmDTO = findByImdbID(imdbID);
        return Film.of(filmDTO.getTitle(),filmDTO.getImdbID());
    }


}
