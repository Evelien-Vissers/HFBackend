package com.novi.HealForce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping

//AdminAuthController is verantwoordelijk voor de authenticatie van admin-gebruikers
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    // POST /admin/login - Log een admin-gebruiker in om toegang te krijgen tot admin endpoints
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginREquest) {
        AuthResponse authResponse = adminAuthService.login(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    // DELETE /admin/logout - Log de admin-gebruiker uit
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout() {
        adminAuthService.logout();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
