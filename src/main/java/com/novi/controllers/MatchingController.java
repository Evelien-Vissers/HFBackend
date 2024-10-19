package com.novi.controllers;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.PotentialMatches;
import com.novi.services.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matching")

public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    // 1. GET - matching/{profileID1}/potential-matches - Vraag een lijst van potentiele matches aan
    @GetMapping("/{profileID}/potential-matches")
    public ResponseEntity<List<PotentialMatches>>findPotentialMatches(@RequestParam Long currentProfileId,
                                                                     @RequestParam String connectionPreference) {
        List<PotentialMatches> matches = matchingService.findPotentialMatches(connectionPreference, currentProfileId);

            //Controleer of er potential matches zijn gevonden
            if (!matches.isEmpty()) {
                return ResponseEntity.ok(matches);
            } else {
                return ResponseEntity.noContent().build();

        }
    }

    // 2. POST - Endpoint om een "Yes" of "No" van een gebruiker vast te leggen voor een ander profiel
    @PostMapping("/choose")
        public ResponseEntity<?> handleMatchAction(@PathVariable Long currentProfileId,
                                                   @PathVariable Long otherProfileId,
                                                   @PathVariable String action) {
            try {
                boolean isMatchCreated = false;

                // Controleer of de gebruiker "Yes" of "No" heeft gekozen
                if ("yes".equalsIgnoreCase(action)) {
                    // Behandel de "Yes" keuze
                    isMatchCreated = matchingService.handleYesPress(currentProfileId, otherProfileId);

                    if (isMatchCreated) {
                        // Er is een match gemaakt tussen beide gebruikers
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "A match is created!");
                        response.put("matchID", matchingService.getMatchId(currentProfileId, otherProfileId));
                        return ResponseEntity.status(HttpStatus.CREATED).body(response);

                    } else {
                        // Alleen de status is bijgewerkt, maar nog geen match
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Your interest has been recorded. Waiting for the other user to accept.");
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    }
                } else if ("Next".equalsIgnoreCase(action)) {
                    // Behandel de "Next" keuze
                   matchingService.handleNoPress(currentProfileId, otherProfileId);

                    // Geef een reactie terug dat de "Next" is geregistreerd
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "You have chosen 'Next' for this match. This match will not be pursued further.");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    // Ongeldige actie
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Invalid action. Please choose either 'yes' or 'no'.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

            } catch (Exception e) {
                // Foutafhandeling
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "An error occurred while processing the match.");
                errorResponse.put("error", e.getMessage());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }

}}




