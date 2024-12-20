package com.novi.controllers;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProfile(
            @RequestPart(value = "profileData", required = true) ProfileInputDTO profileInputDTO,
            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic) {

        try {
            profileService.saveProfile(profileInputDTO, profilePic);
            return ResponseEntity.status(HttpStatus.CREATED).body("Heal Force Profile Successfully Created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating profile.");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfileOutputDTO> getUserProfileByID(@PathVariable Long id) {
        ProfileOutputDTO profile = profileService.getUserProfileByProfileID(id);
        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID " + id + " not Found");
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/{id}/potential-matches")
    public ResponseEntity<List<PotentialMatchesOutputDTO>> findPotentialMatches() {
        List<PotentialMatchesOutputDTO> matches = profileService.findPotentialMatches();
        if (!matches.isEmpty()) {
            return ResponseEntity.ok(matches);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
        profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
            throw new ResourceNotFoundException("Profile with ProfileID " + id + " not found");
        }
    }
}
