package com.filmreview.dto.requests;

import javax.validation.constraints.NotEmpty;

public class GradeRequestBody {

    @NotEmpty(message = "The grade cannot be empty")
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
