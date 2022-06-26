package com.filmreview.domain.dto;


import com.filmreview.domain.Comment;
import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class ReviewDTO {

    private UUID id;

    @NotEmpty(message = "The grade cannot be empty")
    private Double grade;

    @NotEmpty(message = "The reviewer cannot be empty")
    private ReviewerDTO reviewer;

    private CommentDTO comment;

    public static ReviewDTO of(Review review){
        var dto = new ReviewDTO();
        dto.id = review.getId();
        dto.grade = review.getGrade();
        dto.reviewer = ReviewerDTO.of(review.getReviewer());
        dto.comment = CommentDTO.of(review.getComment());

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public Double getGrade() {
        return grade;
    }

    public ReviewerDTO getReviewer() {
        return reviewer;
    }

    public CommentDTO getComment() {
        return comment;
    }
}
