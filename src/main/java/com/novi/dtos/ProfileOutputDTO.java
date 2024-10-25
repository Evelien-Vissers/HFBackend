package com.novi.dtos;

import java.time.LocalDate;
import java.time.YearMonth;

public class ProfileOutputDTO {

    private LocalDate dateOfBirth;
    private String city;
    private String country;
    private String gender;
    private String healthChallenge;
    private YearMonth diagnosisDate;
    private String healingChoice;
    private String connectionPreference;
    private String profilePic;
    private String healforceName;
    private boolean hasCompletedQuestionnaire;

    // Constructors
    public ProfileOutputDTO() {
    }

    public ProfileOutputDTO(LocalDate dateOfBirth, String city, String country, String gender, String healthChallenge, YearMonth diagnosisDate, String healingChoice, String connectionPreference, String profilePic, String healforceName, Boolean hasCompletedQuestionnaire) {
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.healthChallenge = healthChallenge;
        this.diagnosisDate = diagnosisDate;
        this.healingChoice = healingChoice;
        this.connectionPreference = connectionPreference;
        this.profilePic = profilePic;
        this.healforceName = healforceName;
        this.hasCompletedQuestionnaire = hasCompletedQuestionnaire;
    }

    // Getters and Setters
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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

    public Boolean getHasCompletedQuestionnaire() {
        return hasCompletedQuestionnaire;
    }
    public void setHasCompletedQuestionnaire(Boolean hasCompletedQuestionnaire) {
        this.hasCompletedQuestionnaire = hasCompletedQuestionnaire;
    }
}

