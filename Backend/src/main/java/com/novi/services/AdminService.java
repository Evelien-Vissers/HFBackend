package main.java.com.novi.services;

import main.java.com.novi.repositories.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    // Constructor injection
    public AdminService(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
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
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get details of a specific user by ID
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Delete a specific user by ID
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
    }

}
