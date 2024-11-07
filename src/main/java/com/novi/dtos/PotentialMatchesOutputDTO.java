package com.novi.dtos;

public class PotentialMatchesOutputDTO {

    private String healforceName;
    private String healthChallenge;
    private String healingChoice;
    private String profilePicUrl;
    private String city;
    private String country;

    // Constructors
    public PotentialMatchesOutputDTO(String healforceName, String healthChallenge, String healingChoice, String profilePicUrl, String city, String country) {
        this.healforceName = healforceName;
        this.healthChallenge = healthChallenge;
        this.healingChoice = healingChoice;
        this.profilePicUrl = profilePicUrl;
        this.city = city;
        this.country = country;
    }

    // Getters and Setters
    public String getHealforceName() {
        return healforceName;
    }

    public void setHealforceName(String healforceName) {
        this.healforceName = healforceName;
    }

    public String getHealthChallenge() {
        return healthChallenge;
    }

    public void setHealthChallenge(String healthChallenge) {
        this.healthChallenge = healthChallenge;
    }
    public String getHealingChoice() {
        return healingChoice;
    }
    public void setHealingChoice(String healingChoice) {
        this.healingChoice = healingChoice;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
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

    // Optional: Override toString for better logging/debugging
    @Override
    public String toString() {
        return "PotentialMatchesOutputDTO{" +
                "healforceName='" + healforceName + '\'' +
                ", healthChallenge='" + healthChallenge + '\'' +
                ", healingChoice='" + healingChoice + '\'' +
                ", profilePic='" + profilePicUrl + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}