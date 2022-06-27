package com.filmreview.domain;


import com.filmreview.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Reviewers")
public class Reviewer extends AbstractEntity{

    private String name;

    private String username;

    private String email;

    private String password;

    private Integer score = 0;

    @Enumerated(EnumType.STRING)
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

    public Integer getScore() {
        return score;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reviewer reviewer = (Reviewer) o;
        return Objects.equals(name, reviewer.name)
                && Objects.equals(username, reviewer.username)
                && Objects.equals(email, reviewer.email);
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
                ", profileType=" + profileType +
                '}';
    }

    public void addExperience() {
        score += 1;
        verifyTypeProfile();
    }

    private void verifyTypeProfile() { // TODO: criar teste
        if (score > 1000) return;
        profileType = profileType.level(score);
    }

    public void updateToModerator() {
        profileType = ProfileType.MODERATOR;
        score = 1001;
    }
}
