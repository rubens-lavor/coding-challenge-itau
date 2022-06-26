package com.filmreview.dto;

import com.filmreview.domain.QuoteComment;

import java.util.UUID;

public final class QuoteCommentDTO {
    //TODO: considerar criar um dto abstrato para os dtos de comentários
    // com médoto genérico para instaciar o dto
    private UUID id;
    private String description;

    public static QuoteCommentDTO of(QuoteComment quoteComment) {
        var dto = new QuoteCommentDTO();
        dto.id = quoteComment.getId();
        dto.description = quoteComment.getDescription();

        return dto;
    }
}
