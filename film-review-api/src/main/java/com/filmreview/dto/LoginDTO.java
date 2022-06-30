package com.filmreview.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
