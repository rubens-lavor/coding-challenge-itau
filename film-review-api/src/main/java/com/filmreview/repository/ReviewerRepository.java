package com.filmreview.repository;

import com.filmreview.domain.Reviewer;
import com.filmreview.exception.BadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {

    @Override
    default Reviewer getOne(UUID id) {
        return findById(id).orElseThrow(() -> new BadRequestException("Reviewer not found by id"));
    }

    Boolean existsByUsernameOrEmail(String username, String email);
}
