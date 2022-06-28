package com.filmreview.domain;

import com.filmreview.entity.AbstractCommentEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Quotes")
public class QuoteComment extends AbstractCommentEntity {

    @OneToOne
    @JoinColumn(name = "sender_id")
    private Reviewer sender;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static QuoteComment of(String description, Comment comment, Reviewer sender) {
        var quote = new QuoteComment();
        quote.description = description;
        quote.comment = comment;
        quote.sender = sender;
        quote.createdAt = LocalDateTime.now();

        return quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QuoteComment that = (QuoteComment) o;
        return Objects.equals(sender, that.sender) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sender, comment);
    }

    @Override
    public String toString() {
        return "QuoteComment{" +
                "sender=" + sender +
                ", description='" + description + '\'' +
                '}';
    }
}
