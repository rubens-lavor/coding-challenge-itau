package com.filmreview.domain;

import com.filmreview.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class EvaluationComment extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "sender_id")
    private Reviewer sender;

    @Enumerated(EnumType.STRING)
    private EvaluationType type;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public static EvaluationComment of(Reviewer sender, EvaluationType type, Comment comment){
        var evaluation = new EvaluationComment();
        evaluation.sender = sender;
        evaluation.type = type;
        evaluation.comment = comment;

        return evaluation;
    }

    public Reviewer getSender() {
        return sender;
    }

    public EvaluationType getType() {
        return type;
    }

    public Comment getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationComment that = (EvaluationComment) o;
        return Objects.equals(sender, that.sender) && type == that.type && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, type, comment);
    }

    @Override
    public String toString() {
        return "EvaluationComment{" +
                "sender=" + sender +
                ", type=" + type +
                '}';
    }
}
