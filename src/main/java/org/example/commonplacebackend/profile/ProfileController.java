package org.example.commonplacebackend.profile;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ProfileResponseDto getProfileById(@PathVariable Integer id) {
        return profileService.getProfileById(id);
    }

    @GetMapping
    public ResponseEntity<?> getProfileByUsername(@RequestParam String username) {
        ProfileResponseDto profileResponseDto = profileService.getProfileByName(username);
        if (profileResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profileResponseDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponseDto createProfile(@RequestBody ProfileRequestDto profileRequestDto) {
        return profileService.createProfile(profileRequestDto);
    }

    @PatchMapping("/{id}")
    public ProfileResponseDto updateProfile(@PathVariable Integer id, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        return profileService.updateProfile(id, profileRequestDto);
    }
}
