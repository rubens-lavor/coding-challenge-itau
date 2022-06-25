package com.filmreview.domain;


import com.filmreview.domain.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Reviewer extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    private String password;

    private Integer points = 0;

    private ProfileType profileType = ProfileType.READER;

//    @ManyToOne
//    @JoinColumn(name = "mention_id")
//    private AbstractCommentEntity mention;

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
