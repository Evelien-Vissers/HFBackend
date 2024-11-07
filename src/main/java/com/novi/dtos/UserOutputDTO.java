package com.novi.dtos;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserOutputDTO {

    private Long userId;
    private Long profileId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDate registrationDate;
    private LocalDateTime lastLogin;
    private Boolean AcceptedPrivacyStatementUserAgreement;


    // Constructors
    public UserOutputDTO() {
    }

    public UserOutputDTO(Long userId, Long profileId, String firstName, String lastName, String email, String role, LocalDate registrationDate, LocalDateTime lastLogin) {
        this.userId = userId;
        this.profileId = profileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.registrationDate = registrationDate;
        this.lastLogin = lastLogin;
        this.AcceptedPrivacyStatementUserAgreement = false;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getAcceptedPrivacyStatementUserAgreement() {
        return AcceptedPrivacyStatementUserAgreement;
    }

    public void setAcceptedPrivacyStatementUserAgreement(Boolean AcceptedPrivacyStatementUserAgreement) {
        this.AcceptedPrivacyStatementUserAgreement = AcceptedPrivacyStatementUserAgreement;
    }
}


