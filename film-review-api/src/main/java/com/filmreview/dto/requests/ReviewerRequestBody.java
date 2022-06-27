package com.filmreview.dto.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ReviewerRequestBody {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid format address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
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
