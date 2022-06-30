package com.filmreview.service;

import com.filmreview.domain.Reviewer;
import com.filmreview.dto.ReviewerDTO;
import com.filmreview.dto.requests.ReviewerRequestBody;
import com.filmreview.repository.ReviewerRepository;
import com.filmreview.utils.Rule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    @Transactional
    public ReviewerDTO createReviewer(ReviewerRequestBody dto) {
        Rule.check(!reviewerRepository.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail()),
                "The user already exists");

        var passwordEncoder = new BCryptPasswordEncoder();

        var reviewer = Reviewer.of(dto.getName(), dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        return ReviewerDTO.of(reviewerRepository.save(reviewer));
    }
}
