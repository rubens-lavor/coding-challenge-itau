package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractCommentEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment extends AbstractCommentEntity {

    private Boolean isRepeated = false;

    private Integer like = 0;

    private Integer dislike = 0;

    @OneToMany(mappedBy = "comment")
    private final List<ReplyComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private final List<QuoteComment> quotes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public static Comment of(String description) {
        var comment = new Comment();
        comment.description = description;

        return comment;
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

    public List<ReplyComment> getReplies() {
        return replies;
    }

    public List<QuoteComment> getQuotes() {
        return quotes;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
