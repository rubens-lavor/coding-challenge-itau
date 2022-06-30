package com.filmreview.controller;

import com.filmreview.dto.ReviewerDTO;
import com.filmreview.dto.requests.ReviewerRequestBody;
import com.filmreview.service.ReviewerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ReviewerController {

    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<ReviewerDTO> createReviewer(@RequestBody @Valid ReviewerRequestBody body) {
        return new ResponseEntity<>(reviewerService.createReviewer(body), HttpStatus.CREATED);
    }
}
