package com.filmreview.repository;

import com.filmreview.domain.Comment;
import com.filmreview.exception.BadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Override
    default Comment getOne(UUID id) {
        return findById(id).orElseThrow(() -> new BadRequestException("Comment not found by id"));
    }
}
