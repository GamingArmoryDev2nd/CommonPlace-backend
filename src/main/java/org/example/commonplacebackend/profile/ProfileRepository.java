package org.example.commonplacebackend.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
    Optional<Profile> findByUsername(String username);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
