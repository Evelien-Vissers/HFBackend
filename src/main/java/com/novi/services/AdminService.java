package com.novi.services;

import com.novi.dtos.AdminOutputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.Admin;
import com.novi.mappers.AdminMapper;
import com.novi.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService; //
    private final AdminMapper adminMapper;

    // Constructor injection
    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.adminMapper = new AdminMapper();
    }

    // Authenticatie admin using email and password
    public boolean authenticate(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return admin.getPassword().equals(password);
        }
        return false;
    }

    // Get admin details by ID
    public AdminOutputDTO getAdminById(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        return adminOptional.map(adminMapper::toAdminOutputDTO).orElse(null);
    }

    // Get all users in the application
    public List<UserOutputDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get details of a specific user by ID
    public UserOutputDTO getUserById(Long id) {
        return userService.getUserById(id);
    }

    // Delete a specific user by ID
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
