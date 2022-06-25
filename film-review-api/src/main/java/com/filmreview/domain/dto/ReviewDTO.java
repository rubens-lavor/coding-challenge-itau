package com.filmreview.domain.dto;


import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class ReviewDTO {

    private UUID id;

    private Double grade;

    @NotEmpty(message = "The reviewer cannot be empty")
    private ReviewerDTO reviewer;

    @NotEmpty(message = "The comment cannot be empty")
    private String comment;

    public static ReviewDTO of(Review review){
        var dto = new ReviewDTO();
        dto.id = review.getId();
        dto.grade = review.getGrade();
        dto.reviewer = ReviewerDTO.of(review.getReviewer());
        dto.comment = review.getComment();

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public ReviewerDTO getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }
}
