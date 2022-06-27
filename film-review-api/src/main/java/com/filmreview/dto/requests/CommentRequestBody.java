package com.filmreview.dto.requests;

import javax.validation.constraints.NotEmpty;

public class CommentRequestBody {
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
