package com.filmreview.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ReviewGradeDTO {

    @NotEmpty(message = "The grade cannot be empty")
    @NotNull(message = "notnull")
    private String grade;

    @NotEmpty(message = "The reviewer cannot be empty")
    private String reviewerId;

    public String getGrade() {
        return grade;
    }

    public String getReviewerId() {
        return reviewerId;
    }
}
