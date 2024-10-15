package com.novi.services;

import com.novi.dtos.AdminOutputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.Admin;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.AdminMapper;
import com.novi.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    private final UserService userService; //
    private final AdminMapper adminMapper;

    // Constructor injection
    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.adminMapper = new AdminMapper();
    }

    // 1a. Authenticatie admin using email and password
    public boolean authenticate(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return admin.getPassword().equals(password);
        }
        return false;
    }
    // 1b. Haal admin op obv het emailadres
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));
    }
    //1b. Werk lastLogin van Admin bij
    public void updateLastLogin(Admin admin) {
        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);
    }

    // 2. Get admin details by ID
    public AdminOutputDTO getAdminById(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        return adminOptional.map(adminMapper::toAdminOutputDTO).orElse(null);
    }

    // 3. Get all users in the application
    public List<UserOutputDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // 4. Get details of a specific user by ID
    public UserOutputDTO getUserById(Long id) {
        return userService.getUserById(id);
    }

    // 5. Delete a specific user by ID
    public boolean deleteUser(Long id) {
        userService.deleteUser(id);
        return false;
    }
}
