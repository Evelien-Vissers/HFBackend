package com.novi.controllers;

import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.exceptions.UnauthorizedException;
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

    // 1. POST - Registreer een nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInputDTO userInputDTO) {
        userService.registerUser(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // 2. POST - Gebruiker inloggen
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserInputDTO userInputDTO) {
        boolean isAuthenticated = userService.authenticate(userInputDTO.getEmail(), userInputDTO.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    // 3. GET /users/{Id} - Haal informatie op van een specifieke gebruiker
    @GetMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long Id) {
        UserOutputDTO userDTO = userService.getUserById(Id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    // 4. PUT /users/{Id} - Werk informatie van een specifieke gebruiker bij
    @PutMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable Long Id, @RequestBody UserInputDTO userDTO) {
        UserOutputDTO updatedUserDTO = userService.updateUser(Id, new UserInputDTO());
        if (userDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    // 5. DELETE /users/{Id} - Verwijder een specifieke gebruiker
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Id) {
        boolean isDeleted = userService.deleteUser(Id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    // SECURITY USERCONTROLLERS


}

