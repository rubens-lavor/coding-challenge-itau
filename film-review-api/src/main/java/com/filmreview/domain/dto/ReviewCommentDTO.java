package com.filmreview.domain.dto;

import com.filmreview.domain.Review;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class ReviewCommentDTO {
    @NotEmpty(message = "The comment is required")
    private String description;

    @NotEmpty(message = "The reviewer ID is required")
    private String reviewerId;

    public String getDescription() {
        return description;
    }

    public String getReviewerId() {
        return reviewerId;
    }
}
