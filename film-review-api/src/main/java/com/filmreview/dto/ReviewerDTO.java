package com.filmreview.dto;


import com.filmreview.domain.Reviewer;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class ReviewerDTO {

    private UUID id;
    private String name;
    private String username;

    public static ReviewerDTO of(Reviewer reviewer){
        var dto = new ReviewerDTO();
        dto.id = reviewer.getId();
        dto.name = reviewer.getName();
        dto.username = reviewer.getUsername();

        return dto;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

}
