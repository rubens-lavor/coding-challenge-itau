package com.filmreview.domain.repository;

import com.filmreview.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilmRepository extends JpaRepository<Film, UUID> {

}
