package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Matching extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingId;

    private Boolean statusProfile1;
    private Boolean statusProfile2;
    private LocalDateTime matchDate;

    @ManyToMany
    @JoinTable(
            name = "profile_matching",
            joinColumns = @JoinColumn(name = "matching_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profiles;

    // Constructors
    public Matching() {
    }

    public Matching(Boolean statusProfile1, Boolean statusProfile2, LocalDateTime matchDate, Set<Profile> profiles) {
        this.statusProfile1 = statusProfile1;
        this.statusProfile2 = statusProfile2;
        this.matchDate = matchDate;
        this.profiles = profiles;
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

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
}
