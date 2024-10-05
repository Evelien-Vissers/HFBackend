package main.java.com.novi.services;

import main.java.com.novi.dto.UserDTO;
import main.java.com.novi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import main.java.com.novi.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to register a user
    public void registerUser(User user) {
        userRepository.save(user);
    }

    // Logica voor authenticatie
    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a specific user by ID
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Update de gegevens van de gebruiker
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            user.setVerifiedEmail(updatedUser.getVerifiedEmail());
            user.setHasCompletedQuestionnaire(updatedUser.getHasCompletedQuestionnaire());
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Methode om User om te zetten naar UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setVerifiedEmail(user.getVerifiedEmail());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setHasCompletedQuestionnaire(user.getHasCompletedQuestionnaire());
        return userDTO;
    }
}
