package com.filmreview.domain.dto;

import com.filmreview.domain.Grade;

import java.util.UUID;

public class GradeDTO {
    private UUID id;
    private ReviewerDTO reviewer;
    private Double grade;

    public static GradeDTO of(Grade grade){
        var dto = new GradeDTO();
        dto.id = grade.getId();
        dto.reviewer = ReviewerDTO.of(grade.getReviewer());
        dto.grade = grade.getGrade();

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public ReviewerDTO getReviewer() {
        return reviewer;
    }

    public Double getGrade() {
        return grade;
    }
}
