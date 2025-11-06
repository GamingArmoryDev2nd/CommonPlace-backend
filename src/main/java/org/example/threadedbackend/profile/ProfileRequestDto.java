package org.example.threadedbackend.profile;

import jakarta.validation.constraints.NotBlank;

public class ProfileRequestDto extends ProfileResponseDto {
    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
