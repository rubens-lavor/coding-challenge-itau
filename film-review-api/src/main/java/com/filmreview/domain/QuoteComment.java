package com.filmreview.domain;

import com.filmreview.entity.AbstractCommentEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class QuoteComment extends AbstractCommentEntity {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Override
    public String toString() {
        return "QuoteComment{" +
                "description='" + description + '\'' +
                '}';
    }
}
