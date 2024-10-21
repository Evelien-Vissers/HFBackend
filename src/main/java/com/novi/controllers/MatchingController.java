package com.novi.controllers;

import com.novi.services.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/matching")

public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    // 1. POST - Endpoint om een "Yes" of "Next" van een gebruiker vast te leggen voor een ander profiel
    @PostMapping("/choose")
        public ResponseEntity<Map<String, Object>> handleMatchAction(@RequestParam UUID profile2Id,
                                                                     @RequestParam String action) {
        Map<String, Object> response = new HashMap<>();

                // Controleer of de gebruiker "Yes" of "No" heeft gekozen
                if ("yes".equalsIgnoreCase(action)) {
                    // Behandel de "Yes" keuze
                    boolean isMatchCreated = matchingService.handleYesPress(profile2Id);
                    if (isMatchCreated) {
                        // Er is een match gemaakt tussen beide gebruikers
                        response.put("message", "A match is created!");
                        return ResponseEntity.status(HttpStatus.CREATED).body(response);

                    } else {
                        // Alleen de status is bijgewerkt, maar nog geen match
                        response.put("message", "Your interest has been recorded. Waiting for the other user to accept.");
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    }
                } else if ("Next".equalsIgnoreCase(action)) {
                    // Behandel de "Next" keuze
                   matchingService.handleNoPress(profile2Id);
                    // Geef een reactie terug dat de "Next" is geregistreerd
                    response.put("message", "You have chosen 'Next' for this match. This match will not be pursued further.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Ongeldige actie
                    response.put("message", "Invalid action. Please choose either 'yes' or 'no'.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

}}




