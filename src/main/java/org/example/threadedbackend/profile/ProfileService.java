package org.example.threadedbackend.profile;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileResponseDto getProfileById(Integer id) {
        return ProfileMapper.toDto(profileRepository.findById(id).orElseThrow(EntityNotFoundException::new) );
    }

    public ProfileResponseDto getProfileByName(String name) {
        return ProfileMapper.toDto(profileRepository.findByUsername(name).orElse(null));
    }

    public ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto) {
        if (profileRepository.existsByUsername(profileRequestDto.getUsername())) {
            throw new EntityExistsException("Profile already exists!");
        }
        Profile profile = ProfileMapper.fromDto(profileRequestDto);
        profile.setPassword(passwordEncoder.encode(profileRequestDto.getPassword()));
        return ProfileMapper.toDto(profileRepository.save(profile));
    }

    public ProfileResponseDto updateProfile(Integer id, ProfileRequestDto profileRequestDto) {
        Profile existingProfile = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        update(existingProfile, ProfileMapper.fromDto(profileRequestDto));
        return ProfileMapper.toDto(profileRepository.save(existingProfile));
    }

    public void update(Profile oldProfile, Profile newProfile) {
        oldProfile.setUsername(newProfile.getUsername());
        oldProfile.setEmail(newProfile.getEmail());
    }

    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return profileRepository.existsByEmail(email);
    }
}
