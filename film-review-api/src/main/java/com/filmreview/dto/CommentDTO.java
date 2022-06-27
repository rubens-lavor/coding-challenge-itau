package com.filmreview.dto;

import com.filmreview.domain.Comment;
import com.filmreview.domain.EvaluationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentDTO {
    private UUID id;
    private String description;
    private Boolean isRepeated;
    private long like = 0;
    private long dislike = 0;
    private LocalDateTime createdAt;
    private List<ReplyCommentDTO> replies;
    private List<QuoteCommentDTO> quotes;

    public static CommentDTO of(Comment comment) {
        var dto = new CommentDTO();
        dto.id = comment.getId();
        dto.description = comment.getDescription();
        dto.isRepeated = comment.getRepeated();
        dto.createdAt = comment.getCreatedAt();
        dto.replies = comment.getReplies().stream().map(ReplyCommentDTO::of).collect(Collectors.toList());
        dto.quotes = comment.getQuotes().stream().map(QuoteCommentDTO::of).collect(Collectors.toList());
        dto.like = comment.getEvaluations()
                .stream()
                .filter(it -> it.getType().equals(EvaluationType.LIKE))
                .collect(Collectors.toList())
                .size();
        dto.dislike = comment.getEvaluations()
                .stream()
                .filter(it -> it.getType().equals(EvaluationType.DISLIKE))
                .collect(Collectors.toList())
                .size();

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

    public long getLike() {
        return like;
    }

    public long getDislike() {
        return dislike;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ReplyCommentDTO> getReplies() {
        return replies;
    }

    public List<QuoteCommentDTO> getQuotes() {
        return quotes;
    }
}
