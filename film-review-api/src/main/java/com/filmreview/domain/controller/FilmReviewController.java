package com.filmreview.domain.controller;

import com.filmreview.domain.dto.*;
import com.filmreview.domain.service.FilmReviewService;
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
    public ResponseEntity<List<FilmDTO>> filmList() { //TODO: deve estar logado, ou seja, conta criada.
        return ResponseEntity.ok(filmReviewService.listAll());
    }

    @GetMapping(path = "/{imdbID}")
    public ResponseEntity<FilmDTO> findByImdbID(@PathVariable String imdbID) {
        return ResponseEntity.ok(filmReviewService.findByImdbID(imdbID));
    }

    @PostMapping("/create-account")
    public ResponseEntity<ReviewerDTO> createReviewer(@RequestBody @Valid ReviewerDTO reviewerDTO) {
        return new ResponseEntity<>(filmReviewService.createReviewer(reviewerDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{imdbID}/review/grade") // qualquer um pode dar nota, desde que esteja logado
    public ResponseEntity<FilmDTO> sendGrade(@PathVariable String imdbID, @RequestBody @Valid ReviewGradeDTO reviewGradeDTO) {
        return new ResponseEntity<>(filmReviewService.sendGradeReview(imdbID, reviewGradeDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{imdbID}/review/comment")
    public ResponseEntity<FilmDTO> sendComment(@PathVariable String imdbID, @RequestBody @Valid ReviewCommentDTO reviewCommentDTO) {
        return new ResponseEntity<>(filmReviewService.sendCommentReview(imdbID, reviewCommentDTO), HttpStatus.CREATED);
    }

    // endpoint para responder comentários nos reviews
    @PostMapping("/review/{id}/reply")
    public ResponseEntity<FilmDTO> replyToReview(@PathVariable UUID id, @RequestBody @Valid ReviewDTO reviewDTO) {
        return new ResponseEntity<>(filmReviewService.replyToReview(id, reviewDTO), HttpStatus.CREATED);
    }

//    @PostMapping("/{id}/review/response")
//    public ResponseEntity<Review> sendComment(@RequestBody @Valid ReviewerDTO reviewerDTO) {
//        return new ResponseEntity<>(filmReviewService.sendComment(reviewerDTO), HttpStatus.CREATED);
//    }

    @DeleteMapping(path = "/{id}") // TODO: verificar, mas acredito que seja possível deletar um comentário!!
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //TODO: métodos para like e dislike

    // no seu próprio comentário citar outros comentários.
}
