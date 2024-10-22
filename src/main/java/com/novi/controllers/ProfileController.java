package com.novi.controllers;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.MiniProfile;
import com.novi.entities.PotentialMatches;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        // Sla profielgegevens op (de UUID wordt automatisch gegenereerd bij het opslaan)
        profileService.saveProfile(profileInputDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Heal Force Profile Successfully Created!");
    }

    // 2. GET /profiles/{profileID} - Haal profiel op van een specifieke gebruiker
    @GetMapping("{id}")
    public ResponseEntity<ProfileOutputDTO> getUserProfileByID(@PathVariable Long id) {
        ProfileOutputDTO profile = profileService.getUserProfileByProfileID(id);
        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID " + id + " not Found");
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // 3. GET - Haal MiniProfile op van gebruiker ZELF via ProfileID op wanneer hij op 'Start Matching' drukt (als weergave van zijn eigen MatchingProfile op de MatchingPage)
    @GetMapping("/{id}/mini-profile")
    public ResponseEntity<MiniProfile> getMiniProfile(@PathVariable Long id) {
        MiniProfile miniProfile = profileService.getMiniProfile(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mini profile with ProfileID " + id + " not found"));

        return ResponseEntity.ok(miniProfile);
    }

    // 4. GET - profile/{id}/potential-matches - Vraag een lijst van potentiele matches aan
    @GetMapping("/{id}/potential-matches")
    public ResponseEntity<List<PotentialMatchesOutputDTO>> findPotentialMatches(@PathVariable Long id) {
        List<PotentialMatchesOutputDTO> matches = profileService.findPotentialMatches();

        //Controleer of er potential matches zijn gevonden
        if (!matches.isEmpty()) {
            return ResponseEntity.ok(matches);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    // 5. PUT /profiles/{id}/update - Werk profiel van een specifieke gebruiker bij
    @PutMapping("/{id}/update")
    public ResponseEntity<ProfileOutputDTO> updateProfile(@PathVariable Long id,
                                                          @RequestBody ProfileInputDTO profileInputDTO) {
        ProfileOutputDTO updatedProfile = profileService.updateProfile(id, profileInputDTO);
        if (updatedProfile == null) {
            throw new ResourceNotFoundException("Profile with ProfileID " + id + " not found");
        }
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // 6. DELETE - Verwijder een profiel (zonder dat 'User' wordt verwijderd)
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
        profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
            throw new ResourceNotFoundException("Profile with ProfileID " + id + " not found");
        }
    }
}
