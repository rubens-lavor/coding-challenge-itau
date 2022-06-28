package com.filmreview.dto.requests;

import javax.validation.constraints.NotEmpty;

public class ReplyAndQuoteRequestBody {

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Reviewer id cannot be empty")
    private String senderId;

    public String getDescription() {
        return description;
    }

    public String getSenderId() {
        return senderId;
    }
}
