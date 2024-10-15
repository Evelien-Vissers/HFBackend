package com.novi.dtos;

import java.time.LocalDateTime;

public class PotentialMatchesOutputDTO {

    private Long matchingId;
    private Boolean statusProfile1;
    private Boolean statusProfile2;
    private LocalDateTime matchDate;

    // Constructors
    public PotentialMatchesOutputDTO(String healforceName, String healthChallenge, String profilePic, String location) {
    }

    public PotentialMatchesOutputDTO(Long matchingId, Boolean statusProfile1, Boolean statusProfile2, LocalDateTime matchDate) {
        this.matchingId = matchingId;
        this.statusProfile1 = statusProfile1;
        this.statusProfile2 = statusProfile2;
        this.matchDate = matchDate;
    }

    // Getters and Setters
    public Long getMatchingId() {
        return matchingId;
    }

    public void setMatchingId(Long matchingId) {
        this.matchingId = matchingId;
    }

    public Boolean getStatusProfile1() {
        return statusProfile1;
    }

    public void setStatusProfile1(Boolean statusProfile1) {
        this.statusProfile1 = statusProfile1;
    }

    public Boolean getStatusProfile2() {
        return statusProfile2;
    }

    public void setStatusProfile2(Boolean statusProfile2) {
        this.statusProfile2 = statusProfile2;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }
}

