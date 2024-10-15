package com.novi.controllers;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.MiniProfile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // 1. POST - /profiles/new - Maak een nieuw profiel aan
    @PostMapping("/new")
    public ResponseEntity<String> createProfile(@RequestBody ProfileInputDTO profileInputDTO) {
        // Sla profielgegevens op
        profileService.saveProfile(profileInputDTO);
        //Genereer en sla de profileID op
        Long profileID = profileService.generateProfileID(profileInputDTO);
        profileService.saveProfileID(profileID);

        return ResponseEntity.status(HttpStatus.CREATED).body("Heal Force Profile Successfully Created!");
    }

    // 2. GET /profiles/{profileID} - Haal profiel op van een specifieke gebruiker
    @GetMapping("{profileID}")
    public ResponseEntity<ProfileOutputDTO> getUserProfileByProfileID(@PathVariable Long profileID) {
        ProfileOutputDTO profile = profileService.getUserProfileByProfileID(profileID);
        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ProfileID " + profileID + " not Found");
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // 3. GET - Haal MiniProfile op van gebruiker ZELF via ProfileID op wanneer hij op 'Start Matching' drukt (als weergave van zijn eigen MatchingProfile op de MatchingPage)
    @GetMapping("/{profileID}/mini-profile")
    public ResponseEntity<MiniProfile> getMiniProfile(@PathVariable Long profileID) {
        MiniProfile miniProfile = profileService.getMiniProfile(profileID);
        if (miniProfile == null) {
            throw new ResourceNotFoundException("Matching profile with ProfileID " + profileID + " not found");
        }
        return new ResponseEntity<>(miniProfile, HttpStatus.OK);
    }


    // 5. PUT /profiles/{profileId}/update - Werk profiel van een specifieke gebruiker bij
    @PutMapping("/{profileID}/update")
    public ResponseEntity<ProfileOutputDTO> updateProfile(@PathVariable Long profileID, @RequestBody ProfileInputDTO profileInputDTO) {
        ProfileOutputDTO updatedProfile = profileService.updateProfile(profileID, profileInputDTO);
        if (updatedProfile == null) {
            throw new ResourceNotFoundException("Profile with ProfileID " + profileID + " not found");
        }
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // 6. DELETE - Verwijder een profiel (zonder dat 'User' wordt verwijderd)
    @DeleteMapping("/{profileID}/delete")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileID) {
        try {
        profileService.deleteProfile(profileID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
            throw new ResourceNotFoundException("Profile with ProfileID " + profileID + " not found");
        }
    }
}
