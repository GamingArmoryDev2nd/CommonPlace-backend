package org.example.threadedbackend.profile;

public class ProfileMapper {
    public static Profile fromDto(ProfileRequestDto requestDto) {
        Profile profile = new Profile();
        profile.setUsername(requestDto.getUsername());
        profile.setEmail(requestDto.getEmail());
        profile.setPassword(requestDto.getPassword());
        return profile;
    }

    public static ProfileResponseDto toDto(Profile profile) {
        if (profile == null) {
            return null;
        }

        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        profileResponseDto.setId(profile.getId());
        profileResponseDto.setUsername(profile.getUsername());
        profileResponseDto.setEmail(profile.getEmail());
        return profileResponseDto;
    }
}
