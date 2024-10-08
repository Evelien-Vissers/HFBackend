package com.novi.controllers;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileMatchingOutputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileInputDTO profileInputDTO;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
        this.profileInputDTO = profileInputDTO;
    }

    // POST - Maak een nieuw profiel aan
    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody ProfileInputDTO profileInputDTO) {
        // Sla profielgegevens op
        profileService.saveProfile(ProfileInputDTO);
        //Genereer en sla de profileID op
        Long profileID = profileService.generateProfileID(ProfileInputDTO);
        profileService.saveProfileID(profileID);

        return ResponseEntity.status(HttpStatus.CREATED).body("Heal Force Profile Successfully Created!");
    }

    // GET /users/{Id}/profile - Haal profiel op van een specifieke gebruiker
    @GetMapping
    public ResponseEntity<ProfileOutputDTO> getUserProfileByProfileID(@PathVariable Long profileID) {
        ProfileOutputDTO profile = profileService.getUserProfileByProfileID(profileID);
        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ProfileID " + profileID + " not Found");
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // GET - Haal MatchingProfile op van gebruiker ZELF via ProfileID op wanneer hij op 'Start Matching' drukt (als weergave van zijn eigen MatchingProfile op de MatchingPage)
    @GetMapping("/{profileID}/matching-profile")
    public ResponseEntity<ProfileMatchingOutputDTO> getMatchingProfile(@PathVariable Long profileID) {
        ProfileMatchingOutputDTO matchingProfile = profileService.getMatchingProfile(profileID);
        if (matchingProfile == null) {
            throw new ResourceNotFoundException("Matching profile with ProfileID " + profileID + " not found");
        }
        return new ResponseEntity<>(matchingProfile, HttpStatus.OK);
    }


    // PUT /users/{Id}/profile - Werk profiel van een specifieke gebruiker bij
    @PutMapping("/{profileID}")
    public ResponseEntity<ProfileOutputDTO> updateProfile(@PathVariable Long profileID, @RequestBody ProfileInputDTO profileInputDTO) {
        ProfileOutputDTO updatedProfile = profileService.updateProfile(profileID, profileInputDTO);
        if (updatedProfile == null) {
            throw new ResourceNotFoundException("Profile with ProfileID " + profileID + " not found");
        }
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // DELETE - Verwijder een profiel
    @DeleteMapping("/{profileID}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileID) {
        try {
        profileService.deleteProfile(profileID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
            throw new ResourceNotFoundException("Profile with ProfileID " + profileID + " not found");
        }
    }
}
