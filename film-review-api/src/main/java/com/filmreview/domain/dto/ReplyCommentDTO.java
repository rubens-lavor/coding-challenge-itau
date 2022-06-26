package com.filmreview.domain.dto;

import com.filmreview.domain.ReplyComment;

import java.util.UUID;

public class ReplyCommentDTO {
    private UUID id;
    private String description;

    public static ReplyCommentDTO of(ReplyComment replyComment) {
        var dto = new ReplyCommentDTO();
        dto.id = replyComment.getId();
        dto.description = replyComment.getDescription();

        return dto;
    }
}