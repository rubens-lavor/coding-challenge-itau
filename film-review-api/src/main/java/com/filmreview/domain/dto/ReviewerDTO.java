package com.filmreview.domain.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReviewerDTO {

    @NotEmpty(message = "The reviewer name cannot be empty")
    private String name;

    private String username;

    private String email;

    private String password;

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
