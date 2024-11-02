package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="matching")

public class Matching extends BaseEntity {

    //Onderstaande relaties geven weer dat 'Profile' onderdeel kan zijn van meedere matches, maar een specifieke match betreft altijd 2 specifieke profielen.

    //Verwijzing naar het profiel van ingelogde gebruiker (profile1)
    @ManyToOne
    @JoinColumn(name = "profile1", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Profile profile1;

    //Verwijzing naar het profiel van de andere gebruiker (profile2)
    @ManyToOne
    @JoinColumn(name = "profile2", nullable = true, foreignKey =@ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Profile profile2;

    //Statussen om aan te geven of elk profiel "Yes" of "Next" heeft gekozen
    @Column(name = "status_profile1")
    private Boolean statusProfile1;

    @Column(name = "status_profile2")
    private Boolean statusProfile2;

    //Geeft aan of er een volledige match is gemaakt
    @Column(name = "match_status")
    private Boolean matchStatus;

    //Datum en tijd wanneer de match werd aangemaakt
    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;


    // Constructors
    public Matching() {
    }

    public Matching(Profile profile1, Profile profile2, Boolean statusProfile1, Boolean statusProfile2, LocalDateTime matchDate, Boolean matchStatus) {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.statusProfile1 = statusProfile1;
        this.statusProfile2 = statusProfile2;
        this.matchStatus = matchStatus;
        this.matchDate = matchDate;
    }


    public Profile getProfile1() {
        return profile1;
    }
    public void setProfile1(Profile profile1) {
        this.profile1 = profile1;
    }
    public Profile getProfile2() {
        return profile2;
    }
    public void setProfile2(Profile profile2) {
        this.profile2 = profile2;
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

    public Boolean getMatchStatus() {
        return matchStatus;
    }
    public void setMatchStatus(Boolean matchStatus) {
        this.matchStatus = matchStatus;
    }

}
