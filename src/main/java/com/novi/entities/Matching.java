package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="matching")

public class Matching extends BaseEntity {

    @Column(name = "status_Profile1")
    private Boolean statusProfile1;

    @Column(name = "status_Profile2")
    private Boolean statusProfile2;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    //Relaties
    @ManyToMany(mappedBy = "matching")
    private List<Profile> profiles;



    // Constructors
    public Matching() {
    }

    public Matching(Boolean statusProfile1, Boolean statusProfile2, LocalDateTime matchDate, Set<Profile> profiles) {
        this.statusProfile1 = statusProfile1;
        this.statusProfile2 = statusProfile2;
        this.matchDate = matchDate;
    }

    // Getters and Setters

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

    public List<Profile> getProfiles() {
        return profiles;
    }
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

}
