package com.novi.dtos;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public class ProfileInputDTO {

    private LocalDate dateOfBirth;
    private String city;
    private String country;
    private String gender;
    private String healthChallenge;
    private YearMonth diagnosisDate;
    private String healingChoice;
    private String connectionPreference;
    private String profilePicUrl;
    private String healforceName;
    private Boolean hasCompletedQuestionnaire;

    // Constructors
    public ProfileInputDTO() {
    }

    public ProfileInputDTO(LocalDate dateOfBirth, String city, String country, String gender, String healthChallenge, YearMonth diagnosisDate, String healingChoice, String connectionPreference, String profilePicUrl, String healforceName, Boolean hasCompletedQuestionnaire) {
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.healthChallenge = healthChallenge;
        this.diagnosisDate = diagnosisDate;
        this.healingChoice = healingChoice;
        this.connectionPreference = connectionPreference;
        this.profilePicUrl = profilePicUrl;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
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


