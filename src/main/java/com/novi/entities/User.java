package com.novi.entities;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")

public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "User"; //Default value is "User"

    @Column(name ="accepted_policies", nullable = false)
    private Boolean acceptedPrivacyStatementUserAgreement;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "has_completed_questionnaire", nullable = false)
    private Boolean hasCompletedQuestionnaire = false; //Default value = false.

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true; // Standaardwaarde voor nieuwe gebruikers

    @Column(nullable = true, length = 600)
    private String question;


    // Dit is de owner van de relatie. De "mappedBy" geeft aan dat de Profile-entiteit niet de eigenaar van de relatie is en dat de kolom die de relatie beheert (de foreign key) in de User-entiteit zit
    @OneToOne
    @JoinColumn(name="profile_user", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    //Constructors
    //No-arg constructor (verplicht voor JPA)
    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String password, Boolean acceptedPrivacyStatementUserAgreement) {
        super(); // Roept de BaseEntity constructor
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.acceptedPrivacyStatementUserAgreement = acceptedPrivacyStatementUserAgreement;
        this.role = "User"; // Default role
        this.registrationDate = LocalDate.now(); // set to currentDate
        this.lastLogin = null;
        this.hasCompletedQuestionnaire = false; // Default to false
        this.enabled = true;
        this.question = null;
    }

    // Getters and Setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAcceptedPrivacyStatementUserAgreement() {
        return acceptedPrivacyStatementUserAgreement;
    }

    public void setAcceptedPrivacyStatementUserAgreement(Boolean acceptedPrivacyStatementUserAgreement) {
        this.acceptedPrivacyStatementUserAgreement = acceptedPrivacyStatementUserAgreement;
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

    public Boolean getHasCompletedQuestionnaire() {
        return hasCompletedQuestionnaire;
    }

    public void setHasCompletedQuestionnaire(Boolean hasCompletedQuestionnaire) {
        this.hasCompletedQuestionnaire = hasCompletedQuestionnaire;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
}
