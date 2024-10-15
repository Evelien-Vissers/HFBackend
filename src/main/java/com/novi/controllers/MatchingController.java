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
    public ResponseEntity<?> getPotentialMatches(@PathVariable Long profileID) { // Het vraagteken betekent dat de response niet per se een vaste lijst hoeft te zijn, maar een combinatie van een bericht in de Matchlijst.
        try {
            //Verzoek om lijst van potential matches via de matchingService
            List<PotentialMatchesOutputDTO> potentialMatches = matchingService.getPotentialMatches(profileID);

            //Controleer of er potential matches zijn gevonden
            if (potentialMatches.isEmpty()) {
                //Als er geen matches zijn, geef de volgende melding:
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Unfortunately we could not find fitting matches for you today. Try again later!");
                return ResponseEntity.status(HttpStatus.OK).body(response); //Gebruik '200 OK' omdat het geen fout is, maar informatief
            }

            //Als er matches zijn, geef de melding met de lijst van matches terug
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Here are your potential Heal Force matches!");
            response.put("matches", potentialMatches);

            return ResponseEntity.ok(response); // Stuur response met de melding en de matches

        } catch (IllegalArgumentException e) {
            // Foutafhandeling voor ongeldige profileID's
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "You are not authorized to view potential matches at this time.");
            errorResponse.put("error", e.getMessage());  // Optioneel: Voeg de foutboodschap toe voor debuggen (optioneel)

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);  // Stuur 401 Unauthorized terug
        }
    }

    // 2. POST - Endpoint om een "Yes" of "No" van een gebruiker vast te leggen voor een ander profiel
    @PostMapping
        public ResponseEntity<?> handleMatchAction(@PathVariable Long profileID1, @PathVariable Long profileID2, @PathVariable String action) {
            try {
                boolean isMatchCreated = false;

                // Controleer of de gebruiker "Yes" of "No" heeft gekozen
                if ("yes".equalsIgnoreCase(action)) {
                    // Behandel de "Yes" keuze
                    isMatchCreated = matchingService.handleYesPress(profileID1, profileID2);

                    if (isMatchCreated) {
                        // Er is een match gemaakt tussen beide gebruikers
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "A match is created created!");
                       // response.put("matchID", matchingService.getMatchId(profileID1, profileID2));

                        return ResponseEntity.status(HttpStatus.CREATED).body(response);
                    } else {
                        // Alleen de status is bijgewerkt, maar nog geen match
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Your interest has been recorded. Waiting for the other user to accept.");
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    }
                } else if ("no".equalsIgnoreCase(action)) {
                    // Behandel de "No" keuze
                   matchingService.handleNoPress(profileID1, profileID2);

                    // Geef een reactie terug dat de "No" is geregistreerd
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "You have chosen 'No' for this match. This match will not be pursued further.");
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




