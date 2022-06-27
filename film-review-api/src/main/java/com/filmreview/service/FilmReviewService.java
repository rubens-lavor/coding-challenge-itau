package com.filmreview.service;

import com.filmreview.domain.*;
import com.filmreview.dto.requests.*;
import com.filmreview.dto.responses.OMDdResponseBody;
import com.filmreview.exception.BadRequestException;
import com.filmreview.repository.*;
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
    private final EvaluationCommentRepository evaluationCommentRepository;
    private final RestTemplateBuilder restTemplateBuilder;

    public FilmReviewService(FilmRepository filmRepository,
                             ReviewerRepository reviewerRepository,
                             ReviewRepository reviewRepository,
                             CommentRepository commentRepository, EvaluationCommentRepository evaluationCommentRepository, RestTemplateBuilder restTemplateBuilder
    ) {
        this.filmRepository = filmRepository;
        this.reviewerRepository = reviewerRepository;
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
        this.evaluationCommentRepository = evaluationCommentRepository;
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

    private OMDdResponseBody getResponseBodyOMDd(String url) {
        var objectResponse = restTemplateBuilder.build().getForObject(url, OMDdResponseBody.class);

        assert Objects.nonNull(objectResponse);
        if (objectResponse.Response.equals("False")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, objectResponse.Error);
        }
        return objectResponse;
    }

    @Transactional
    public ReviewerDTO createReviewer(ReviewerRequestBody dto) {
        var reviewer = Reviewer.of(dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPassword());
        return ReviewerDTO.of(reviewerRepository.save(reviewer));
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
    public ReviewDTO sendCommentReview(String imdbID, CommentRequestBody dto) {
        var film = getFilm(imdbID);
        var reviewer = getReviewer(dto.getReviewerId());
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

        addExperience(reviewer);
//        reviewer.addExperience();
//        reviewerRepository.save(reviewer);

        // var dtoReturn = FilmDTO.of(film);
        return ReviewDTO.of(review);

    }

    @Transactional
    public ReviewDTO sendGradeReview(String imdbID, GradeRequestBody dto) {
        var reviewer = getReviewer(dto.getReviewerId());
        var film = getFilm(imdbID);

        var review = getReview(reviewer, film);
        review.addGrade(Double.valueOf(dto.getGrade()));

        filmRepository.save(film);

        addExperience(reviewer);
//        reviewer.addExperience();
//        reviewerRepository.save(reviewer);

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

    public CommentDTO replyToComment(UUID id, ReplyAndQuoteRequestBody dto) {
        // TODO: receber o id do reviewer!!! ReplyAndQuote devem conter o id de quem escreveu.
        // TODO: adicionar atributo createdDate doo tipo LocaDateTime.now(), na classe reply e quote!!! IMPORTANTE !!!
        var sender = getReviewer(dto.getSenderId());
        var comment = commentRepository.getOne(id);
        var reply = ReplyComment.of(dto.getDescription(), comment, sender);
        comment.addReply(reply);
        addExperience(sender);
        return saveComment(comment);
    }

    @Transactional
    private void addExperience(Reviewer reviewer) {
        reviewer.addExperience();
        reviewerRepository.save(reviewer);
    }

    @Transactional
    private Reviewer getReviewer(String id) {
        return reviewerRepository.getOne(getId(id));
    }

    @Transactional
    public CommentDTO quoteToComment(UUID id, ReplyAndQuoteRequestBody dto) {
        var sender = getReviewer(dto.getSenderId());
        var comment = commentRepository.getOne(id);
        var quote = QuoteComment.of(dto.getDescription(), comment, sender);
        comment.addQuote(quote);
        return saveComment(comment);
    }

    @Transactional
    private CommentDTO saveComment(Comment comment) {
        return CommentDTO.of(commentRepository.save(comment));
    }

    private UUID getId(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new BadRequestException("id not valid: " + e.getMessage());
        }
    }

    @Transactional
    public CommentDTO evaluationComment(UUID id, EvaluationCommentRequestBody dto) {
        var sender = getReviewer(dto.getSenderId());
        var comment = commentRepository.getOne(id);
        if (evaluationCommentRepository.existsBySenderAndComment(sender,comment)) {
            throw new BadRequestException("The same user cannot rate a the same comment twice");
        }
        var type = EvaluationType.valueOf(dto.getType());
        var evaluation = EvaluationComment.of(sender, type, comment);
        comment.addEvaluation(evaluation);

        return saveComment(comment);
    }
}
