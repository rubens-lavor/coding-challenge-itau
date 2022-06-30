package com.filmreview.controller;

import com.filmreview.dto.*;
import com.filmreview.dto.requests.*;
import com.filmreview.service.FilmReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("films")
public class FilmReviewController {

    private final FilmReviewService filmReviewService;

    public FilmReviewController(FilmReviewService filmReviewService) {
        this.filmReviewService = filmReviewService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> filmList() {
        return ResponseEntity.ok(filmReviewService.listAll());
    }

    @GetMapping(path = "/reviewers")
    public ResponseEntity<List<ReviewerDTO>> reviewerList() {
        return ResponseEntity.ok(filmReviewService.listAllReviewer());
    }

    @GetMapping(path = "/{imdbID}")
    public ResponseEntity<FilmDTO> findByImdbID(@PathVariable String imdbID) {
        return ResponseEntity.ok(filmReviewService.findByImdbID(imdbID));
    }

//    @PostMapping("/create-account")
//    public ResponseEntity<ReviewerDTO> createReviewer(@RequestBody @Valid ReviewerRequestBody body) {
//        return new ResponseEntity<>(filmReviewService.createReviewer(body), HttpStatus.CREATED);
//    }

    @PostMapping("/{imdbID}/review/grade")
    public ResponseEntity<ReviewDTO> sendGrade(@PathVariable String imdbID, @RequestBody @Valid GradeRequestBody body) {
        return new ResponseEntity<>(filmReviewService.sendGradeReview(imdbID, body), HttpStatus.CREATED);
    }

    @PostMapping("/{imdbID}/review/comment")
    public ResponseEntity<ReviewDTO> sendComment(@PathVariable String imdbID, @RequestBody @Valid CommentRequestBody body) {
        return new ResponseEntity<>(filmReviewService.sendCommentReview(imdbID, body), HttpStatus.CREATED);
    }

    @PostMapping("/review/comment/{id}/reply")
    public ResponseEntity<CommentDTO> replyToComment(@PathVariable UUID id, @RequestBody @Valid ReplyAndQuoteRequestBody body) {
        return new ResponseEntity<>(filmReviewService.replyToComment(id, body), HttpStatus.CREATED);
    }

    @PostMapping("/review/comment/{id}/quote")
    public ResponseEntity<CommentDTO> quoteToComment(@PathVariable UUID id, @RequestBody @Valid ReplyAndQuoteRequestBody body) {
        return new ResponseEntity<>(filmReviewService.quoteToComment(id, body), HttpStatus.CREATED);
    }

    @PostMapping("/review/comment/{id}/evaluation")
    public ResponseEntity<CommentDTO> evaluationComment(@PathVariable UUID id, @RequestBody @Valid EvaluationCommentRequestBody body) {
        return new ResponseEntity<>(filmReviewService.evaluationComment(id, body), HttpStatus.CREATED);
    }

    @PutMapping("/moderator/{moderatorId}/promote/{reviewerId}")
    public ResponseEntity<Void> promoteToModerator(@PathVariable UUID moderatorId, @PathVariable UUID reviewerId) {
        filmReviewService.promoteToModerator(moderatorId, reviewerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/moderator/{moderatorId}/repeated-comment/{commentId}")
    public ResponseEntity<Void> repeatedComment(@PathVariable UUID moderatorId, @PathVariable UUID commentId) {
        filmReviewService.repeatedComment(moderatorId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/moderator/{moderatorId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID moderatorId, @PathVariable UUID commentId) {
        filmReviewService.deleteComment(moderatorId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
