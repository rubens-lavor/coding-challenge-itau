package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractCommentEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ReplyComment extends AbstractCommentEntity {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Override
    public String toString() {
        return "ReplyComment{" +
                "description='" + description + '\'' +
                '}';
    }
}
