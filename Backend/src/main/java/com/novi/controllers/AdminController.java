package main.java.com.novi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = adminService.authenticate(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    // GET - Haal Admin-details op aan de hand van ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /admin/users - Haal een lijst op van alle gebruikers in de applicatie
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = adminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // GET /admin/users/{userId} - Haal gedetailleerde informatie op van een specifieke gebruiker
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = adminService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // DELETE /admin/users/{userId} - Verwijder een specifieke gebruiker uit de applicatie
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
