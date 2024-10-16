package com.novi.dtos;

import java.time.LocalDate;
import java.time.YearMonth;

public class ProfileInputDTO {

    private LocalDate dateOfBirth;
    private String location;
    private String gender;
    private String healthChallenge;
    private YearMonth diagnosisDate;
    private String healingChoice;
    private String connectionPreference;
    private String profilePic;
    private String healforceName;
    private Long profileID;

    // Constructors
    public ProfileInputDTO() {
    }

    public ProfileInputDTO(LocalDate dateOfBirth, String location, String gender, String healthChallenge, YearMonth diagnosisDate, String healingChoice, String connectionPreference, String profilePic, String healforceName, Long ProfileID) {
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

    public String getHealforceName() {
        return healforceName;
    }

    public void setHealforceName(String healforceName) {
        this.healforceName = healforceName;
    }

    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }
}


