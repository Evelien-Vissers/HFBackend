package com.novi.controllers;

import com.novi.dtos.AdminInputDTO;
import com.novi.dtos.AdminOutputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.Admin;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.exceptions.UnauthorizedException;
import main.java.com.novi.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

//AdminController is verantwoordelijk voor het beheren van gebruikers door de admin
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // POST - Admin Login. Er wordt gebruik gemaakt van een @RequestParam om de e-mail en het wachtwoord van de admin te ontvangen.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminInputDTO adminInputDTO) {
        boolean isAuthenticated = adminService.authenticate(adminInputDTO.getEmail(), adminInputDTO.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            throw new UnauthorizedException("Invalid email or password");
        }
    }
    // GET - Haal Admin-details op aan de hand van ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminOutputDTO> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            AdminOutputDTO adminOutputDTO = new AdminOutputDTO(admin.getEmail(), admin.getLastLogin());
            return ResponseEntity.ok(adminOutputDTO);
        } else {
            throw new ResourceNotFoundException("Admin with ID " + id + " not found");
        }
    }

    // GET /admin/users - Haal een lijst op van alle gebruikers in de applicatie
    @GetMapping("/users")
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        List<UserOutputDTO> users = adminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET /admin/users/{Id} - Haal gedetailleerde informatie op van een specifieke gebruiker
    @GetMapping("/users/{Id}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long Id) {
        UserOutputDTO user = adminService.getUserById(Id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with ID " + Id + " not found");
        }
    }

    // DELETE /admin/users/{Id} - Verwijder een specifieke gebruiker uit de applicatie
    @DeleteMapping("/users/{Id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Id) {
        boolean isDeleted = adminService.deleteUser(Id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
        throw new ResourceNotFoundException("User with ID " + Id + " not found");
    }
}}
