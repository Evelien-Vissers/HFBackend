package com.novi.HealForce.controllers;

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
    // GET /users/{userId}/profile - Haal profiel op van een specifieke gebruiker
    @GetMapping
    public ResponseEntity<ProfileDTO> getUserProfile(@PathVariable Long userId) {
        ProfileDTO profile = profileService.getUserProfile (userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
    // PUT /users/{userId}/profile - Werk profiel van een specifieke gebruiker bij
    @PutMapping
    public ResponseEntity<ProfileDTO> updateUserProfile (
            @PathVariable Long userId,
            @RequestBody ProfileDTO profileDTO) {
        ProfileDTO updatedProfile = profileService.updateUserProfile(userId, profileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }
}
