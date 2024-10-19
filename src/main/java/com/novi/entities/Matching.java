package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="matching")

public class Matching extends BaseEntity {

    @Column(name = "status_currentProfile")
    private Boolean statusCurrentProfile;

    @Column(name = "status_otherProfile")
    private Boolean statusOtherProfile;

    @Column(name = "match_status")
    private Boolean matchStatus;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;


    //Relatie Met 'Profiles'
    @ManyToMany(mappedBy = "matching")
    private Set<Profile> profiles;
    private Profile otherProfile;


    // Constructors
    public Matching() {
    }

    public Matching(Boolean statusCurrentProfile, Boolean statusOtherProfile, LocalDateTime matchDate, Boolean matchStatus, Set<Profile> profiles) {
        this.statusCurrentProfile = statusCurrentProfile;
        this.statusOtherProfile = statusOtherProfile;
        this.matchStatus = matchStatus;
        this.matchDate = matchDate;
    }

    // Getters and Setters

    public Boolean getStatusCurrentProfile() {
        return statusCurrentProfile;
    }

    public void setStatusCurrentProfile(Boolean statusProfile1) {
        this.statusCurrentProfile = statusCurrentProfile;
    }

    public Boolean getStatusOtherProfile() {
        return statusOtherProfile;
    }

    public void setStatusOtherProfile(Boolean statusProfile2) {
        this.statusOtherProfile = statusOtherProfile;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }
    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
    public Boolean getMatchStatus() {
        return matchStatus;
    }
    public void setMatchStatus(Boolean matchStatus) {
        this.matchStatus = matchStatus;
    }

}
