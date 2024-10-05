package main.java.com.novi.services;

import main.java.com.novi.dto.AdminDTO;
import main.java.com.novi.dto.UserDTO;
import main.java.com.novi.entities.Admin;
import main.java.com.novi.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService; //

    // Constructor injection
    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
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
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    // Get all users in the application
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get details of a specific user by ID
    public UserDTO getUserById(Long Id) {
        return userService.getUserById(Id);
    }

    // Delete a specific user by ID
    public void deleteUser(Long Id) {
        userService.deleteUser(Id);
        }

    //Helper method to convert Admin entity to AdminDTO
    private AdminDTO convertToAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setLastLogin(admin.getLastLogin());
        return adminDTO;
    }
}
