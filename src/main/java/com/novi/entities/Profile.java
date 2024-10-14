package com.novi.entities;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String gender;

    @Column(name = "health_challenge", nullable = false)
    private String healthChallenge;

    @Column(name = "diagnosis_date", nullable = false)
    private YearMonth diagnosisDate;

    @Column(name = "healing_choice", nullable = false)
    private String healingChoice;

    @Column(name = "connection_preference", nullable = false)
    private String connectionPreference;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "healforce_Name")
    private String healforceName;

    @Column(name = "profileID", unique = true)
    private Long profileID;

    //Relaties
    //One-To-One met 'User'
    @OneToOne(mappedBy = "profile")
    private User user;

    //Many-To-Many met 'Matching'
    @ManyToMany
    @JoinTable(
            name = "profile_matches",
            joinColumns = @JoinColumn(name = "profileID"),
            inverseJoinColumns = @JoinColumn(name = "matchingID")
    )
    private List<Matching> matching;

    //One-To-Many met 'Message'



    // Default constructor
    public Profile() {
        super(); //Aanroepen van de constructor van BaseEntity
    }

    // Constructor with all fields
    public Profile(LocalDate dateOfBirth, String location, String gender, String healthChallenge,
                   YearMonth diagnosisDate, String healingChoice, String connectionPreference, String profilePic, String healforceName, Long profileID) {
        super();
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.gender = gender;
        this.healthChallenge = healthChallenge;
        this.diagnosisDate = diagnosisDate;
        this.healingChoice = healingChoice;
        this.connectionPreference = connectionPreference;
        this.profilePic = profilePic;
        this.healforceName = healforceName;
        this.profileID = profileID;
    }

    // Getters and Setters
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthChallenge() {
        return healthChallenge;
    }

    public void setHealthChallenge(String healthChallenge) {
        this.healthChallenge = healthChallenge;
    }

    public YearMonth getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(YearMonth diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getHealingChoice() {
        return healingChoice;
    }

    public void setHealingChoice(String healingChoice) {
        this.healingChoice = healingChoice;
    }

    public String getConnectionPreference() {
        return connectionPreference;
    }

    public void setConnectionPreference(String connectionPreference) {
        this.connectionPreference = connectionPreference;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getHealforceName() { return healforceName; }

    public void setHealforceName(String healforceName) {this.healforceName = healforceName; }

    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Matching> getMatching() {
        return matching;
    }

    public void setMatching(List<Matching> matching) {
        this.matching = matching;
    }
}

