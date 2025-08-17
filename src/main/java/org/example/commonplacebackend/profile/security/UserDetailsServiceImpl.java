package org.example.commonplacebackend.profile.security;

import org.example.commonplacebackend.profile.Profile;
import org.example.commonplacebackend.profile.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public UserDetailsServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden: " + username));

        return new UserDetailsImpl(profile, profile.getPassword());
    }
}
