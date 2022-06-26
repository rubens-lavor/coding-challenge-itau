package com.filmreview.domain.dto;


import com.filmreview.domain.Review;
import com.filmreview.domain.Reviewer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ReviewerDTO {

    private Long id;

    @NotEmpty(message = "The reviewer name cannot be empty")
    private String name;

    @NotEmpty(message = "The reviewer username cannot be empty")
    private String username;

    @NotEmpty(message = "The reviewer email cannot be empty")
    private String email;

    @NotEmpty(message = "The reviewer password cannot be empty")
    private String password;

    public static ReviewerDTO of(Reviewer reviewer){
        var dto = new ReviewerDTO();
        dto.id = reviewer.getId();
        dto.name = reviewer.getName();
        dto.username = reviewer.getUsername();
        dto.email = reviewer.getEmail();

        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
