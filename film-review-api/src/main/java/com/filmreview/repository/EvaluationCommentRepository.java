package com.filmreview.repository;

import com.filmreview.domain.Comment;
import com.filmreview.domain.EvaluationComment;
import com.filmreview.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EvaluationCommentRepository extends JpaRepository<EvaluationComment, UUID> {
    Boolean existsBySenderAndComment(Reviewer sender, Comment comment);
}
