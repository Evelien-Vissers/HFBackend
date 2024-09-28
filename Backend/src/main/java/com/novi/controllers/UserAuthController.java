package com.novi.HealForce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

//UserAuthController is verantwoordelijk voor zowel de registratie als het inloggen van gebruikers
public class UserAuthController {
    private final AuthService authService;

    public UserAuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /auth/register - Registreer een nieuwe gebruiker in de applicatie
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = authService.register(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);

    }
    // POST /auth/login - Log een gebruiker in door hun inloggegevens te verifieren
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
