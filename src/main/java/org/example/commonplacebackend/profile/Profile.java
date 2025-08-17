package org.example.commonplacebackend.profile;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String username;
    private String password;
    private String email;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
