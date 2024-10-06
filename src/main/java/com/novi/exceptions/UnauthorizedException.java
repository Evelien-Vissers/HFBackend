package com.novi.exceptions;
//Gegooid bij mislukte inlogpogingen

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
