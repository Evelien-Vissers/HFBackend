package com.novi.controllers;

import com.novi.dtos.CurrentMatchesOutputDTO;
import com.novi.services.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/match")

public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/yes-press/{profile2Id}")
        public ResponseEntity<String> handleYesPress(@PathVariable Long profile2Id) {
        boolean isMatched = matchingService.handleYesPress(profile2Id);
        if (isMatched) {
            return ResponseEntity.ok("Match created!");
        } else {
            return ResponseEntity.ok("Waiting for the other user to respond with 'Yes'.");
        }
    }

    @PostMapping("/next-press/{profile2Id}")
        public ResponseEntity<String> handleNextPress(@PathVariable Long profile2Id) {
        matchingService.handleNextPress(profile2Id);
        return ResponseEntity.ok("Skipped to the next profile");
    }

    @GetMapping("/my-matches")
    public ResponseEntity<List<CurrentMatchesOutputDTO>> showMyMatches() {
        List<CurrentMatchesOutputDTO> matches = matchingService.getMyMatches();
        return ResponseEntity.ok(matches);
    }
}




