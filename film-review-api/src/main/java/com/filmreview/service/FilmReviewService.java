package com.filmreview.service;

import com.filmreview.domain.Comment;
import com.filmreview.domain.Film;
import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;
import com.filmreview.repository.CommentRepository;
import com.filmreview.repository.FilmRepository;
import com.filmreview.repository.ReviewRepository;
import com.filmreview.repository.ReviewerRepository;
import com.filmreview.dto.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilmReviewService {

    private final FilmRepository filmRepository;
    private final ReviewerRepository reviewerRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final RestTemplateBuilder restTemplateBuilder;

    public FilmReviewService(FilmRepository filmRepository,
                             ReviewerRepository reviewerRepository,
                             ReviewRepository reviewRepository,
                             CommentRepository commentRepository, RestTemplateBuilder restTemplateBuilder
    ) {
        this.filmRepository = filmRepository;
        this.reviewerRepository = reviewerRepository;
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Transactional
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
        return FilmDTO.of(film);
    }

    private ResponseBodyOMDd getResponseBodyOMDd(String url) {
        var objectResponse = restTemplateBuilder.build().getForObject(url, ResponseBodyOMDd.class);

        assert Objects.nonNull(objectResponse);
        if (objectResponse.Response.equals("False")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, objectResponse.Error);
        }
        return objectResponse;
    }

    @Transactional
    public ReviewerDTO createReviewer(ReviewerDTO dto) {
        var reviewer = Reviewer.of(dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPassword());
        return ReviewerDTO.of(reviewerRepository.save(reviewer));
    }

    private Reviewer getReviewer(UUID id) {
        var reviewer = reviewerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));

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

    @Transactional
    public FilmDTO replyToReview(UUID id, ReviewDTO reviewDTO) {
        return null;
    }

    @Transactional
    public ReviewDTO sendCommentReview(String imdbID, ReviewCommentDTO dto) {
        var film = getFilm(imdbID);
        var reviewer = getReviewer(UUID.fromString(dto.getReviewerId()));
        if (reviewer.getProfileType().isReader()) {
            //TODO: Criar classe Rule
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "READER cannot leave comments");
        }
        var comment = Comment.of(dto.getDescription());
        var review = getReview(reviewer, film);
        review.addComment(comment);
        comment.setReview(review);
        film.addReview(review);
        filmRepository.save(film);

        reviewer.addExperience();
        reviewerRepository.save(reviewer);

        // var dtoReturn = FilmDTO.of(film);
        return ReviewDTO.of(review);

    }

    @Transactional
    public ReviewDTO sendGradeReview(String imdbID, ReviewGradeDTO dto) {
        var reviewer = reviewerRepository.findById(UUID.fromString(dto.getReviewerId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
        var film = getFilm(imdbID);

        var review = getReview(reviewer, film);
        review.addGrade(Double.valueOf(dto.getGrade()));

        var filmSaved = filmRepository.save(film);

        reviewer.addExperience();
        reviewerRepository.save(reviewer);

        return ReviewDTO.of(review);

    }

    private Review getReview(Reviewer reviewer, Film film) {
        if (Objects.nonNull(film.getId())) { // acredito que possivel retirar essa verificaçao, ja que agora
            // estu gerando id randomicos ja  quando a classe e instanciada
            var optionalReview = reviewRepository.findByReviewerIdAndFilmId(reviewer.getId(), film.getId());
            if (optionalReview.isPresent()) {
                return optionalReview.get();
            }
        }

        // var review = Review.of(reviewer, film);
        var review = Review.of(reviewer);
        film.addReview(review);
        review.setFilm(film);

        return review;
    }
}
