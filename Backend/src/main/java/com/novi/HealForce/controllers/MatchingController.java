package com.novi.HealForce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class MatchingController {

    private final MatchingService matchingService;
    private final MatchingCriteriaService matchingCriteriaService;

    public MatchingController(MatchingService matchingService, MatchingCriteriaService matchingCriteriaService) {
        this.matchingService = matchingService;
        this.matchingCriteriaService = matchingCriteriaService;
    }
    // GET /users/{userId}/match-criteria - Haal de matching criteria van een specifieke gebruiker op
    @GetMapping
    public ResponseEntity<MatchingCriteriaDTO> getMatchingCriteria(@PathVariable Long userId) {
        MatchingCriteriaDTO matchingCriteria = matchingCriteriaService.getMatchingCriteria(userId);
        return new ResponseEntity<>(matchingCriteria, HttpStatus.OK);
    }
    // PUT /users/{userId}/match-criteria - Werk de matching criteria bij voor een specifieke gebruiker
    @PutMapping
    public ResponseEntity<MatchingCriteriaDTO> updateMatchingCriteria(
            @PathVariable Long userId,
            @RequestBody MatchingCriteriaDTO matchingCriteriaDTO) {
        MatchingCriteriaDTO updatedCriteria = matchingCriteriaService.updateMatchingCriteria(userId, matchingCriteriaDTO);
        return new ResponseEntity<>(updatedCriteria, HttpStatus.OK);
    }
    // GET /users/{userId}/match - Haal een lijst van gematchte gebruikers op
    @GetMapping("/match")
    public ResponseEntity<List<MatchDTO>> getMatches(@PathVariable Long userId) {
        List<MatchDTO> matches = matchingService.findMatches(userId);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
