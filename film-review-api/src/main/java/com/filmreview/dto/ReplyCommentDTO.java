package com.filmreview.dto;

import com.filmreview.domain.ReplyComment;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReplyCommentDTO {
    private UUID id;
    private String description;
    private LocalDateTime createdAt;

    public static ReplyCommentDTO of(ReplyComment replyComment) {
        var dto = new ReplyCommentDTO();
        dto.id = replyComment.getId();
        dto.description = replyComment.getDescription();
        dto.createdAt = replyComment.getCreatedAt();

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
