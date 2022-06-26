package com.filmreview.dto;


import com.filmreview.domain.Review;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReviewDTO {

    private UUID id;

    private UUID filmId;

    private Double grade;

    @NotEmpty(message = "The reviewer cannot be empty")
    private ReviewerDTO reviewer;

    private List<CommentDTO> comments;

    public static ReviewDTO of(Review review){
        var dto = new ReviewDTO();
        dto.id = review.getId();
        dto.filmId = review.getFilm().getId(); // verificar se em alguma condição gera nullpoint
        dto.grade = review.getGrade();
        dto.reviewer = ReviewerDTO.of(review.getReviewer());
        dto.comments = review.getComments().stream().map(CommentDTO::of).collect(Collectors.toList());

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFilmId() {
        return filmId;
    }

    public Double getGrade() {
        return grade;
    }

    public ReviewerDTO getReviewer() {
        return reviewer;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }
}
