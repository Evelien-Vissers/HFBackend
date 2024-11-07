package com.novi.entities;

public class PotentialMatches {

    private String healforceName;
    private String healthChallenge;
    private String healingChoice;
    private String profilePicUrl;
    private String city;
    private String country;

    public PotentialMatches(String healforceName, String healthChallenge, String healingChoice, String profilePicUrl, String city, String country) {
        this.healforceName = healforceName;
        this.healthChallenge = healthChallenge;
        this.healingChoice = healingChoice;
        this.profilePicUrl = profilePicUrl;
        this.city = city;
        this.country = country;
    }

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
        this.city= city;
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
        return "PotentialMatchList{" +
                "healforceName='" + healforceName + '\'' +
                ", healthChallenge='" + healthChallenge + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

