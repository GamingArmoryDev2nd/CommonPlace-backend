package org.example.commonplacebackend.profile.security;

import jakarta.validation.Valid;
import org.example.commonplacebackend.profile.ProfileRequestDto;
import org.example.commonplacebackend.profile.ProfileService;
import org.example.commonplacebackend.profile.security.payload.JwtResponse;
import org.example.commonplacebackend.profile.security.payload.LoginRequest;
import org.example.commonplacebackend.profile.security.payload.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final JWTUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          ProfileService profileService,
                          JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.profileService = profileService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ProfileRequestDto profileRequest) {
        if (profileService.findByUsername(profileRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Benutzername wird bereits verwendet!"));
        }
        else if (profileService.existsByEmail(profileRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Fehler: Diese E-Mail Adresse ist bereits vergeben!"));
        }


        profileService.createProfile(profileRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
