package com.novi.exceptions;
//Gegooid indien een admin of gebruiker niet wordt gevonden obv hun ID.

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
