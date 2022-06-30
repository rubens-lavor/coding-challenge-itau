package com.filmreview.service;

import com.filmreview.domain.*;
import com.filmreview.dto.CommentDTO;
import com.filmreview.dto.FilmDTO;
import com.filmreview.dto.ReviewDTO;
import com.filmreview.dto.ReviewerDTO;
import com.filmreview.dto.requests.*;
import com.filmreview.dto.responses.OMDdResponseBody;
import com.filmreview.exception.BadRequestException;
import com.filmreview.repository.*;
import com.filmreview.utils.Rule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //private final PasswordEncoder passwordEncoder;

    public FilmReviewService(FilmRepository filmRepository,
                             ReviewerRepository reviewerRepository,
                             ReviewRepository reviewRepository,
                             CommentRepository commentRepository,
                             EvaluationCommentRepository evaluationCommentRepository,
                             RestTemplateBuilder restTemplateBuilder
                             /*, PasswordEncoder passwordEncoder*/) {
        this.filmRepository = filmRepository;
        this.reviewerRepository = reviewerRepository;
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
        this.evaluationCommentRepository = evaluationCommentRepository;
        this.restTemplateBuilder = restTemplateBuilder;
        //this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<FilmDTO> listAll(){
        return filmRepository.findAll()
                .stream()
                .map(FilmDTO::of)
                .collect(Collectors.toList());
    }

    public List<ReviewerDTO> listAllReviewer() {
        return reviewerRepository.findAll().stream()
                .map(ReviewerDTO::of)
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
            throw new BadRequestException(objectResponse.Error);
        }
        return objectResponse;
    }

    @Transactional
    public ReviewerDTO createReviewer(ReviewerRequestBody dto) {
        Rule.check(!reviewerRepository.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail()),
                "The user already exists");

        var passwordEncoder = new BCryptPasswordEncoder();

        var reviewer = Reviewer.of(dto.getName(), dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        return ReviewerDTO.of(reviewerRepository.save(reviewer));
    }

    @Transactional
    public ReviewDTO sendCommentReview(String imdbID, CommentRequestBody dto) {
        var film = getFilm(imdbID);
        var reviewer = getReviewer(dto.getReviewerId());

        Rule.check(!reviewer.getProfileType().isReader(), "Reader profile cannot leave comments");

        var comment = Comment.of(dto.getDescription());
        var review = getReview(reviewer, film);

        review.addComment(comment);
        comment.setReview(review);
        film.addReview(review);
        filmRepository.save(film);

        addExperience(reviewer);

        return ReviewDTO.of(review);
    }

    @Transactional
    public ReviewDTO sendGradeReview(String imdbID, GradeRequestBody dto) {
        var reviewer = getReviewer(dto.getReviewerId());
        var film = getFilm(imdbID);

        var review = getReview(reviewer, film);
        review.addGrade(Double.valueOf(dto.getGrade()));
        film.updateRating();
        filmRepository.save(film);

        addExperience(reviewer);

        return ReviewDTO.of(review);
    }

    public CommentDTO replyToComment(UUID id, ReplyAndQuoteRequestBody dto) {
        var sender = getReviewer(dto.getSenderId());
        Rule.check(!sender.getProfileType().isReader(), "Reader profile cannot leave replies");
        var comment = commentRepository.getOne(id);
        var reply = ReplyComment.of(dto.getDescription(), comment, sender);
        comment.addReply(reply);
        addExperience(sender);

        return CommentDTO.of(commentRepository.save(comment));
    }

    @Transactional
    public CommentDTO quoteToComment(UUID id, ReplyAndQuoteRequestBody dto) {
        var sender = getReviewer(dto.getSenderId());
        Rule.check(sender.getProfileType().isAdvancedOrModerator(), "Only advanced profiles and moderators can make quotes");
        var comment = commentRepository.getOne(id);
        var quote = QuoteComment.of(dto.getDescription(), comment, sender);
        comment.addQuote(quote);

        return CommentDTO.of(commentRepository.save(comment));
    }

    @Transactional
    public CommentDTO evaluationComment(UUID id, EvaluationCommentRequestBody dto) {
        var sender = getReviewer(dto.getSenderId());
        Rule.check(sender.getProfileType().isAdvancedOrModerator(),
                "Only advanced profiles and moderators can leave evaluation");

        var comment = commentRepository.getOne(id);
        Rule.check(!evaluationCommentRepository.existsBySenderAndComment(sender,comment),
                "The same user cannot rate the same comment twice");

        var type = EvaluationType.valueOf(dto.getType());
        var evaluation = EvaluationComment.of(sender, type, comment);
        comment.addEvaluation(evaluation);

        return CommentDTO.of(commentRepository.save(comment));
    }

    @Transactional
    public void promoteToModerator(UUID moderatorId, UUID reviewerId) {
        verifyModerator(moderatorId);
        var reviewer = reviewerRepository.getOne(reviewerId);
        reviewer.updateToModerator();
        reviewerRepository.save(reviewer);
    }

    @Transactional
    public void repeatedComment(UUID moderatorId, UUID commentId) {
        verifyModerator(moderatorId);
        var comment = commentRepository.getOne(commentId);
        comment.setRepeated();
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID moderatorId, UUID commentId) {
        verifyModerator(moderatorId);
        var comment = commentRepository.getOne(commentId);
        var review = comment.getReview();
        review.getComments().remove(comment);
        reviewRepository.save(review);
        commentRepository.delete(comment);
    }

    private void verifyModerator(UUID moderatorId) {
        var moderator = reviewerRepository.getOne(moderatorId);
        Rule.check(moderator.getProfileType().isModerator(), "Only Moderator can access the service");
    }

    private Film getFilm(String imdbID) {
        var optionalFilm = filmRepository.findByImdbID(imdbID);
        if (optionalFilm.isPresent()) {
            return optionalFilm.get();
        }
        var filmDTO = findByImdbID(imdbID);
        return Film.of(filmDTO.getTitle(),filmDTO.getImdbID());
    }

    private Review getReview(Reviewer reviewer, Film film) {
        var optionalReview = reviewRepository.findByReviewerIdAndFilmId(reviewer.getId(), film.getId());
        if (optionalReview.isPresent()) {
            return optionalReview.get();
        }

        var review = Review.of(reviewer);
        film.addReview(review);
        review.setFilm(film);

        return review;
    }

    private Reviewer getReviewer(String id) {
        return reviewerRepository.getOne(getId(id));
    }

    private UUID getId(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new BadRequestException("id not valid: " + e.getMessage());
        }
    }

    @Transactional
    private void addExperience(Reviewer reviewer) {
        reviewer.addExperience();
        reviewerRepository.save(reviewer);
    }
}
