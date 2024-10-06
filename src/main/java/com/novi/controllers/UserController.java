package com.novi.controllers;

import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")

//UserController richt zich alleen op het beheren van gebruikersinformatie
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST - Registreer een nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInputDTO userInputDTO) {
        userService.registerUser(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // POST - Gebruiker inloggen
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserInputDTO userInputDTO) {
        boolean isAuthenticated = userService.authenticate(userInputDTO.getEmail(), userInputDTO.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // GET - Haal alle gebruikers op (in DTO-vorm)
    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        List<UserOutputDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /users/{Id} - Haal informatie op van een specifieke gebruiker
    @GetMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long Id) {
        UserOutputDTO userDTO = userService.getUserById(Id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // PUT /users/{Id} - Werk informatie van een specifieke gebruiker bij
    @PutMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable Long Id, @RequestBody UserInputDTO userDTO) {
        UserOutputDTO updatedUserDTO = userService.updateUser(Id, userInputDTO);
        if (userDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // DELETE /users/{Id} - Verwijder een specifieke gebruiker
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Id) {
        boolean isDeleted = userService.deleteUser(Id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

