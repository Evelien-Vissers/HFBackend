package com.novi.dtos;

public class UserInputDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean acceptedPrivacyStatementUserAgreement;


    public UserInputDTO() {
    }

    public UserInputDTO(String firstName, String lastName, String email, String password, Boolean acceptedPrivacyStatementUserAgreement) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.acceptedPrivacyStatementUserAgreement = acceptedPrivacyStatementUserAgreement;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAcceptedPrivacyStatementUserAgreement() {
        return acceptedPrivacyStatementUserAgreement;
    }

    public void setAcceptedPrivacyStatementUserAgreement(Boolean acceptedPrivacyStatementUserAgreement) {
        this.acceptedPrivacyStatementUserAgreement = acceptedPrivacyStatementUserAgreement;
    }

}


