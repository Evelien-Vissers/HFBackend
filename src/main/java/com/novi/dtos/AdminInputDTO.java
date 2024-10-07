package com.novi.dtos;

//AdminInputDTO wordt gebruikt voor het verwerken van inloggegevens.
// Er is voor zowel een AdminInputDTO als een AdminOutputDTO gekozen, omdat een 'normale' DTO niet veilig zou zijn vanwege het gebruik van wachtwoorden.

public class AdminInputDTO {

    private String email;
    private String password;

    // Constructors
    public AdminInputDTO() {
    }

    public AdminInputDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
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
}

