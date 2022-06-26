package com.filmreview.domain.dto;

import com.filmreview.domain.Comment;

import java.util.List;
import java.util.UUID;

public class CommentDTO {
    private String description;
    private Boolean isRepeated;
    private Integer like;
    private Integer dislike;
    private List<ReplyCommentDTO> replies;
    private List<QuoteCommentDTO> quotes;

    public static CommentDTO of(Comment comment) {
        var dto = new CommentDTO();
        dto.description = comment.getDescription();
        return dto;
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
