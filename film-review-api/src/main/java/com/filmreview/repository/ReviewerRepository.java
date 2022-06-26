package com.filmreview.repository;

import com.filmreview.domain.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {
}
