package com.novi.dtos;

public class LoginResponseDTO {
    private String token;
    private Long Id;
    private String role;

    public LoginResponseDTO(String token, Long Id, String role) {
        this.token = token;
        this.Id = Id;
        this.role = role;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getId() {
        return Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
