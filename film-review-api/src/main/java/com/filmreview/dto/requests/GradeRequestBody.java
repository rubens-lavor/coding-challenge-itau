package com.filmreview.dto.requests;

import javax.validation.constraints.NotEmpty;

public class GradeRequestBody {

    @NotEmpty(message = "Grade cannot be empty")
    private String grade;

    @NotEmpty(message = "Reviewer id cannot be empty")
    private String reviewerId;

    public String getGrade() {
        return grade;
    }

    public String getReviewerId() {
        return reviewerId;
    }
}
