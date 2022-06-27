package com.filmreview.dto;

import com.filmreview.domain.QuoteComment;

import java.time.LocalDateTime;
import java.util.UUID;

public final class QuoteCommentDTO {
    private UUID id;
    private String description;
    private LocalDateTime createdAt;

    public static QuoteCommentDTO of(QuoteComment quoteComment) {
        var dto = new QuoteCommentDTO();
        dto.id = quoteComment.getId();
        dto.description = quoteComment.getDescription();
        dto.createdAt = quoteComment.getCreatedAt();

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
