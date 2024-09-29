package main.java.com.novi.controllers;

import main.java.com.novi.dto.UserDTO;
import main.java.com.novi.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.java.com.novi.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping

//UserController richt zich alleen op het beheren van gebruikersinformatie
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    // POST - Registreer een nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // POST - Gebruiker inloggen
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticate(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // GET - Haal alle gebruikers op (in DTO-vorm)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(userService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // GET /users/{userId} - Haal informatie op van een specifieke gebruiker
    @GetMapping("/{Id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long Id) {
        UserDTO userDTO = userService.getUserById(Id)
                .map(userService::convertToDTO)
                .orElse(null);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // PUT /users/{userId} - Werk informatie van een specifieke gebruiker bij
    @PutMapping("/{Id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long Id, @RequestBody UserDTO userDTO) {
        UserDTO userDTO = userService.updateUser(Id, updatedUser)
                .map(userService::convertToDTO)
                .orElse(null);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // DELETE /users/{userId} - Verwijder een specifieke gebruiker
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Id) {
        userService.deleteUser(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

