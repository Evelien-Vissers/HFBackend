package com.novi.controllers;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
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
    public ResponseEntity<String> createProfile(@PathVariable Long ID, @RequestBody ProfileInputDTO profileInputDTO) {
        profileService.saveProfile(Id, profileInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Profile created successfully!");
    }

    // GET /users/{Id}/profile - Haal profiel op van een specifieke gebruiker
    @GetMapping
    public ResponseEntity<ProfileOutputDTO> getUserProfile(@PathVariable Long ID) {
        ProfileOutputDTO profile = profileService.getUserProfile(ID);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // PUT /users/{Id}/profile - Werk profiel van een specifieke gebruiker bij
    @PutMapping("/{id}")
    public ResponseEntity<ProfileOutputDTO> updateUserProfile(
            @PathVariable Long Id,
            @RequestBody ProfileInputDTO profileInputDTO) {
        ProfileOutputDTO updatedProfile = profileService.updateUserProfile(ID, profileInputDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // DELETE - Verwijder een profiel
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.build();
    }

}
