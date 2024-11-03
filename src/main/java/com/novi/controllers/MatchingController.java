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

    @PostMapping("/yes")
        public ResponseEntity<String> handleYesPress(@RequestParam Long profile2Id) {
        boolean isMatched = matchingService.handleYesPress(profile2Id);
        if (isMatched) {
            return ResponseEntity.ok("Match created!");
        } else {
            return ResponseEntity.ok("Waiting for the other user to respond with 'Yes'.");
        }
    }

    @PostMapping("/next")
        public ResponseEntity<String> handleNextPress(@RequestParam Long profile2Id) {
        matchingService.handleNoPress(profile2Id);
        return ResponseEntity.ok("Skipped to the next profile");
    }

    @GetMapping("/my-matches")
    public ResponseEntity<List<CurrentMatchesOutputDTO>> showMyMatches() {
        List<CurrentMatchesOutputDTO> matches = matchingService.getMyMatches();
        return ResponseEntity.ok(matches);
    }

}




