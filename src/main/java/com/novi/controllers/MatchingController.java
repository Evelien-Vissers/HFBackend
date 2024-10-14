package com.novi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/matching")

public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }
    // 1. GET - matching/{profileID1}/potential-matches - Vraag een lijst van potentiele matches aan
    @GetMapping("/{profileID1}/potential-matches")
    public ResponseEntity<?> getPotentialMatches (@PathVariable Long profileID1) { // Het vraagteken betekent dat de response niet per se een vaste lijst hoeft te zijn, maar een combinatie van een bericht in de Matchlijst.

        //Verzoek om lijst van potential matches via de matchingService
        List<PotentialMatchOutputDTO> potentialMatches = matchingService.getPotentialMatches(profileID1);

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

    } catch (Exception e) {
        // Foutafhandeling: als er iets misgaat, stuur een 401 Unauthorized melding terug
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "You are not authorized to view potential matches at this time.");
        errorResponse.put("error", e.getMessage());  // Optioneel: Voeg de foutboodschap toe voor debuggen (optioneel)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);  // Stuur 401 Unauthorized terug
    }
}


