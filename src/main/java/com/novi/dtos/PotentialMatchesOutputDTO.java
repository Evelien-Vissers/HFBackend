package com.novi.dtos;

public class PotentialMatchesOutputDTO {

    private Long id;
    private String healforceName;
    private String healthChallenge;
    private String healingChoice;
    private String profilePicUrl;
    private String city;
    private String country;


    public PotentialMatchesOutputDTO(Long id, String healforceName, String healthChallenge, String healingChoice, String profilePicUrl, String city, String country) {
        this.id = id;
        this.healforceName = healforceName;
        this.healthChallenge = healthChallenge;
        this.healingChoice = healingChoice;
        this.profilePicUrl = profilePicUrl;
        this.city = city;
        this.country = country;
    }
    public PotentialMatchesOutputDTO() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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