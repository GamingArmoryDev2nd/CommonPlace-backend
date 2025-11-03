package org.example.commonplacebackend.place;

import org.example.commonplacebackend.profile.Profile;

public class PlaceRequestDto {
    private String name;
    private String description;
    private Profile profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
