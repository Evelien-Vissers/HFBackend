package com.novi.dtos;

public class UserFirstNameOutputDTO {
    private String firstName;

    public UserFirstNameOutputDTO() {
    }
    public UserFirstNameOutputDTO(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
