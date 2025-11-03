package org.example.commonplacebackend.post;

import jakarta.validation.constraints.NotNull;
import org.example.commonplacebackend.place.Place;
import org.example.commonplacebackend.profile.Profile;

import java.util.Set;

public class PostRequestDto {
    @NotNull
    private String title;
    private String description;
    private String image;

    @NotNull
    private Profile profile;

    private Place place;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlaces(Place place) {
        this.place = place;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
