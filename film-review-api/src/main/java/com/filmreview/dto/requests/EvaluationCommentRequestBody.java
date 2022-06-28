package com.filmreview.dto.requests;

import javax.validation.constraints.NotEmpty;

public class EvaluationCommentRequestBody {

    @NotEmpty(message = "Sender id is required")
    private String senderId;

    @NotEmpty(message = "Evaluation is required")
    private String type;

    public String getSenderId() {
        return senderId;
    }

    public String getType() {
        return type;
    }
}
