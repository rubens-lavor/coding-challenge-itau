package com.filmreview.domain;

import java.util.Objects;

public class Reviewer {

    private String name;

    private String username;

    private String email;

    private String password;

    private Integer points = 0;

    private ProfileType profileType = ProfileType.READER;

    public static Reviewer of(String name, String username, String email, String password){
        var reviewer = new Reviewer();
        reviewer.name = name;
        reviewer.username = username;
        reviewer.email = email;
        reviewer.password = password;

        return reviewer;
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

    public ProfileType getProfileType() {
        return profileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reviewer reviewer = (Reviewer) o;
        return Objects.equals(name, reviewer.name) && Objects.equals(username, reviewer.username) && Objects.equals(email, reviewer.email) && Objects.equals(password, reviewer.password) && profileType == reviewer.profileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, email, password, profileType);
    }

    @Override
    public String toString() {
        return "Reviewer{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profileType=" + profileType +
                '}';
    }
}
