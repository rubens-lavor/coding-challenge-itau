package com.filmreview.domain.dto;

import com.filmreview.domain.Comment;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentDTO {
    private UUID id;
    private String description;
    private Boolean isRepeated;
    private Integer like;
    private Integer dislike;
    private List<ReplyCommentDTO> replies;
    private List<QuoteCommentDTO> quotes;

    public static CommentDTO of(Comment comment) {
        var dto = new CommentDTO();
        dto.id = comment.getId();
        dto.description = comment.getDescription();
        dto.isRepeated = comment.getRepeated();
        dto.like = comment.getLike();
        dto.dislike = comment.getDislike();
        dto.replies = comment.getReplies().stream().map(ReplyCommentDTO::of).collect(Collectors.toList());
        dto.quotes = comment.getQuotes().stream().map(QuoteCommentDTO::of).collect(Collectors.toList());
        return dto;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRepeated() {
        return isRepeated;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public List<ReplyCommentDTO> getReplies() {
        return replies;
    }

    public List<QuoteCommentDTO> getQuotes() {
        return quotes;
    }
}
