package com.filmreview.domain;

import com.filmreview.domain.entity.AbstractCommentEntity;

import javax.persistence.Entity;
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

    public static Comment of(String description) {
        var comment = new Comment();
        comment.description = description;

        return comment;
    }
}
