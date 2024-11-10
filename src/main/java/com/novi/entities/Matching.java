package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="matching")

public class Matching extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "profile1", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Profile profile1;

    @ManyToOne
    @JoinColumn(name = "profile2", nullable = true, foreignKey =@ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Profile profile2;

    @Column(name = "status_profile1")
    private Boolean statusProfile1;

    @Column(name = "status_profile2")
    private Boolean statusProfile2;

    @Column(name = "match_status")
    private Boolean matchStatus;

    @Column(name = "match_date", nullable = true)
    private LocalDateTime matchDate;


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
