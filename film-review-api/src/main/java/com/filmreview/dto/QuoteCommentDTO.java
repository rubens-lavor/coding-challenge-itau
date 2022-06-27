package com.filmreview.dto;

import com.filmreview.domain.QuoteComment;

import java.util.UUID;

public final class QuoteCommentDTO {
    private UUID id;
    private String description;

    public static QuoteCommentDTO of(QuoteComment quoteComment) {
        var dto = new QuoteCommentDTO();
        dto.id = quoteComment.getId();
        dto.description = quoteComment.getDescription();

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
