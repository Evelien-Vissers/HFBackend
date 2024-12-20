package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "profiles")
public class Profile extends BaseEntity {

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "health_challenge", nullable = false)
    private String healthChallenge;

    @Column(name = "diagnosis_date", nullable = false)
    private String diagnosisDate;

    @Column(name = "hospital")
    private String hospital;

    @Column(name = "healing_choice", nullable = false)
    private String healingChoice;

    @Column(name = "connection_preference", nullable = false)
    private String connectionPreference;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @Column(name = "healforce_name", nullable = false, unique = true)
    private String healforceName;

    @Column(name = "has_completed_questionnaire", nullable = false)
    private boolean hasCompletedQuestionnaire;


    @OneToOne(mappedBy = "profile")
    private User user;


    public Profile() {
        super();
    }


    public Profile(LocalDate dateOfBirth, String city, String country, String gender, String healthChallenge,
                   String diagnosisDate, String hospital, String healingChoice, String connectionPreference, String profilePicUrl, String healforceName, Boolean hasCompletedQuestionnaire) {
        super();
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.healthChallenge = healthChallenge;
        this.diagnosisDate = diagnosisDate;
        this.hospital = hospital;
        this.healingChoice = healingChoice;
        this.connectionPreference = connectionPreference;
        this.profilePicUrl = profilePicUrl;
        this.healforceName = healforceName;
        this.hasCompletedQuestionnaire = true;
    }



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

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getHospital() {return hospital;}
    public void setHospital(String hospital) {
        this.hospital = hospital;
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

    public String getHealforceName() { return healforceName; }

    public void setHealforceName(String healforceName) {this.healforceName = healforceName; }

    public Boolean getHasCompletedQuestionnaire() {
        return hasCompletedQuestionnaire;
    }
    public void setHasCompletedQuestionnaire(Boolean hasCompletedQuestionnaire) {
        this.hasCompletedQuestionnaire = hasCompletedQuestionnaire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

