package com.filmreview.domain;

import com.filmreview.entity.AbstractCommentEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Comments")
public class Comment extends AbstractCommentEntity {

    private Boolean isRepeated = false;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<EvaluationComment> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<ReplyComment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private final List<QuoteComment> quotes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public static Comment of(String description) {
        var comment = new Comment();
        comment.description = description;
        comment.createdAt = LocalDateTime.now();

        return comment;
    }

    public Boolean getRepeated() {
        return isRepeated;
    }

    public List<EvaluationComment> getEvaluations() {
        return evaluations;
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

    public void addReply(ReplyComment reply) {
        if (replies.contains(reply)) return;
        replies.add(reply);
    }

    public void addQuote(QuoteComment quote) {
        if (quotes.contains(quote)) return;
        quotes.add(quote);
    }

    public void addEvaluation(EvaluationComment evaluation) {
        evaluations.add(evaluation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(isRepeated, comment.isRepeated)
                && Objects.equals(evaluations, comment.evaluations)
                && Objects.equals(replies, comment.replies)
                && Objects.equals(quotes, comment.quotes)
                && Objects.equals(review, comment.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isRepeated, evaluations, replies, quotes, review);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "isRepeated=" + isRepeated +
                ", evaluations=" + evaluations +
                ", replies=" + replies +
                ", quotes=" + quotes +
                ", description='" + description + '\'' +
                '}';
    }
}
