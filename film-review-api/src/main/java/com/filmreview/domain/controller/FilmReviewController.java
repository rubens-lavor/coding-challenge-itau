package com.filmreview.domain.controller;

import com.filmreview.domain.Film;
import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;
import com.filmreview.domain.dto.FilmDTO;
import com.filmreview.domain.dto.ReviewDTO;
import com.filmreview.domain.dto.ReviewerDTO;
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
    public ResponseEntity<List<Film>> filmList() { // TODO: criar FilmDTO
        return ResponseEntity.ok(filmReviewService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Film> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(filmReviewService.findById(id));
    }

    @PostMapping("/create-reviewer")
    public ResponseEntity<ReviewerDTO> createReviewer(@RequestBody @Valid ReviewerDTO reviewerDTO) {
        return new ResponseEntity<>(filmReviewService.createReviewer(reviewerDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<FilmDTO> sendReview(@PathVariable UUID id, @RequestBody @Valid ReviewDTO reviewDTO) {
        return new ResponseEntity<>(filmReviewService.sendReview(id, reviewDTO), HttpStatus.CREATED);
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
