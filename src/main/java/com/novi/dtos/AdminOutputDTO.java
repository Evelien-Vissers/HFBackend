package com.novi.dtos;

//AdminOutputDTO wordt gebruikt voor het weergeven van admininformatie zoals de laatste login, zonder gevoelige gegevens zoals het wachtwoord.
import java.time.LocalDateTime;

public class AdminOutputDTO {

    private String email;
    private LocalDateTime lastLogin;

    // Constructors
    public AdminOutputDTO() {
    }

    public AdminOutputDTO(String email, LocalDateTime lastLogin) {
        this.email = email;
        this.lastLogin = lastLogin;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}

