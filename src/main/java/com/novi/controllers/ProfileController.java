package main.java.com.novi.controllers;

import main.java.com.novi.entities.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // POST - Maak een nieuw profiel aan
    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody Profile profile) {
        profileService.saveProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body("Profile created successfully");
    }

    // GET /users/{Id}/profile - Haal profiel op van een specifieke gebruiker
    @GetMapping {
        "/{id}"
    }

    public ResponseEntity<ProfileDTO> getUserProfile(@PathVariable Long userId) {
        ProfileDTO profile = profileService.getUserProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // PUT /users/{Id}/profile - Werk profiel van een specifieke gebruiker bij
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody ProfileDTO profileDTO) {
        ProfileDTO updatedProfile = profileService.updateUserProfile(userId, profileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // DELETE - Verwijder een profiel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.build();
    }

}
