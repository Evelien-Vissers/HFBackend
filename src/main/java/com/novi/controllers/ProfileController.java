package com.novi.controllers;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileMatchingOutputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // POST - Maak een nieuw profiel aan
    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody ProfileInputDTO profileInputDTO) {
        // Sla profielgegevens op
        profileService.saveProfile(profileInputDTO);
        //Genereer en sla de profileID op
        Long profileID = profileService.generateProfileID(profileInputDTO);
        profileService.saveProfileID(profileID);

        return ResponseEntity.status(HttpStatus.CREATED).body("Heal Force Profile Successfully Created!");
    }

    // GET /users/{Id}/profile - Haal profiel op van een specifieke gebruiker
    @GetMapping
    public ResponseEntity<ProfileOutputDTO> getUserProfileByProfileID(@PathVariable Long profileID) {
        ProfileOutputDTO profile = profileService.getUserProfileByProfileID(profileID);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found with ProfileID: " + profileID);
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // GET - Haal MatchingProfile op van gebruiker ZELF via ProfileID op wanneer hij op 'Start Matching' drukt (als weergave van zijn eigen MatchingProfile op de MatchingPage)
    @GetMapping("/{profileID}/matching-profile")
    public ResponseEntity<ProfileMatchingOutputDTO> getMatchingProfile(@PathVariable Long profileID) {
        ProfileMatchingOutputDTO matchingProfile = profileService.getMatchingProfile(profileID);
        if (matchingProfile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matching profile not found with ProfileID: " + profileID);
        }
        return new ResponseEntity<>(matchingProfile, HttpStatus.OK);
    }


    // PUT /users/{Id}/profile - Werk profiel van een specifieke gebruiker bij
    @PutMapping("/{profileID}")
    public ResponseEntity<ProfileOutputDTO> updateProfile(@PathVariable Long profileID, @RequestBody ProfileInputDTO profileInputDTO) {
        ProfileOutputDTO updatedProfile = profileService.updateProfile(profileID, profileInputDTO);
        if (updatedProfile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found with ProfileID: " + profileID);
        }
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // DELETE - Verwijder een profiel
    @DeleteMapping("/{profileID}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileID) {
        try {
        profileService.deleteProfile(profileID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.build();
    } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
